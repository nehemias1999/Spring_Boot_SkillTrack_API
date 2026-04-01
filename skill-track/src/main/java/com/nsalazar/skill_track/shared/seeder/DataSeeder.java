package com.nsalazar.skill_track.shared.seeder;

import com.nsalazar.skill_track.course.application.port.in.CreateCourseUseCase;
import com.nsalazar.skill_track.course.application.port.in.CreateCourseUseCase.CreateCourseCommand;
import com.nsalazar.skill_track.course.application.port.in.ManageCoursePrerequisiteUseCase;
import com.nsalazar.skill_track.course.application.port.in.ManageCourseTagUseCase;
import com.nsalazar.skill_track.course.domain.Course;
import com.nsalazar.skill_track.course.domain.CourseCategory;
import com.nsalazar.skill_track.course.domain.CourseDifficulty;
import com.nsalazar.skill_track.course.infrastructure.out.persistence.CourseJpaRepository;
import com.nsalazar.skill_track.enrollment.application.port.in.EnrollStudentUseCase;
import com.nsalazar.skill_track.instructor.application.port.in.CreateInstructorUseCase;
import com.nsalazar.skill_track.instructor.application.port.in.CreateInstructorUseCase.CreateInstructorCommand;
import com.nsalazar.skill_track.instructor.domain.Address;
import com.nsalazar.skill_track.instructor.domain.Instructor;
import com.nsalazar.skill_track.lesson.application.port.in.CreateLessonUseCase;
import com.nsalazar.skill_track.lesson.application.port.in.CreateLessonUseCase.CreateLessonCommand;
import com.nsalazar.skill_track.lesson.domain.LessonType;
import com.nsalazar.skill_track.profile.application.port.in.CreateProfileUseCase;
import com.nsalazar.skill_track.profile.application.port.in.CreateProfileUseCase.CreateProfileCommand;
import com.nsalazar.skill_track.review.application.port.in.CreateReviewUseCase;
import com.nsalazar.skill_track.review.application.port.in.CreateReviewUseCase.CreateReviewCommand;
import com.nsalazar.skill_track.student.application.port.in.CreateStudentUseCase;
import com.nsalazar.skill_track.student.application.port.in.CreateStudentUseCase.CreateStudentCommand;
import com.nsalazar.skill_track.student.domain.Student;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

/**
 * Seeds the database with realistic sample data on application startup.
 *
 * <p>Data is created through the real use-case inbound ports so that all business logic,
 * validations, domain events, and cache evictions fire exactly as they would in production.
 *
 * <p>Idempotency: if any course already exists the seeder skips entirely, so restarting the
 * application does not create duplicate records.
 *
 * <p>Excluded from the {@code test} profile to avoid interfering with integration tests
 * that spin up their own schema via Testcontainers.
 *
 * <h2>Creation order (respects FK dependencies)</h2>
 * <ol>
 *   <li>Instructors</li>
 *   <li>Students</li>
 *   <li>Courses (references instructor ids)</li>
 *   <li>Student profiles</li>
 *   <li>Course prerequisites (references two course ids each)</li>
 *   <li>Course tags</li>
 *   <li>Lessons (references course ids)</li>
 *   <li>Enrollments (references student + course ids)</li>
 *   <li>Reviews (student must already be enrolled)</li>
 * </ol>
 */
@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
@Transactional
public class DataSeeder implements ApplicationRunner {

    private final CreateInstructorUseCase createInstructorUseCase;
    private final CreateStudentUseCase createStudentUseCase;
    private final CreateCourseUseCase createCourseUseCase;
    private final CreateProfileUseCase createProfileUseCase;
    private final ManageCoursePrerequisiteUseCase manageCoursePrerequisiteUseCase;
    private final ManageCourseTagUseCase manageCourseTagUseCase;
    private final CreateLessonUseCase createLessonUseCase;
    private final EnrollStudentUseCase enrollStudentUseCase;
    private final CreateReviewUseCase createReviewUseCase;

    /** Used only for the idempotency check — avoids seeding on subsequent restarts. */
    private final CourseJpaRepository courseJpaRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (courseJpaRepository.count() > 0) {
            log.info("DataSeeder: database already contains data — skipping seed.");
            return;
        }

        log.info("DataSeeder: seeding database with sample data...");

        // ── 1. Instructors ──────────────────────────────────────────────────────
        Instructor alice = createInstructorUseCase.createInstructor(new CreateInstructorCommand(
                "Alice", "Carter", "alice.carter@skilltrack.com",
                "Full-stack web developer with 10 years of Spring Boot experience.",
                new Address("123 Oak St", "San Francisco", "USA")
        ));
        Instructor robert = createInstructorUseCase.createInstructor(new CreateInstructorCommand(
                "Robert", "Kim", "robert.kim@skilltrack.com",
                "Data scientist and machine-learning engineer specialising in Python and TensorFlow.",
                new Address("456 Elm Ave", "New York", "USA")
        ));
        Instructor sarah = createInstructorUseCase.createInstructor(new CreateInstructorCommand(
                "Sarah", "Johnson", "sarah.johnson@skilltrack.com",
                "Certified cybersecurity consultant with a background in ethical hacking.",
                new Address("789 Pine Rd", "Austin", "USA")
        ));
        Instructor david = createInstructorUseCase.createInstructor(new CreateInstructorCommand(
                "David", "Martinez", "david.martinez@skilltrack.com",
                "Mobile developer focused on iOS (Swift) and Android (Kotlin) app development.",
                new Address("321 Maple Ln", "Seattle", "USA")
        ));
        log.info("DataSeeder: created 4 instructors.");

        // ── 2. Students ─────────────────────────────────────────────────────────
        Student emma = createStudentUseCase.createStudent(new CreateStudentCommand(
                "Emma", "Thompson", "emma.thompson@student.com"
        ));
        Student liam = createStudentUseCase.createStudent(new CreateStudentCommand(
                "Liam", "Wilson", "liam.wilson@student.com"
        ));
        Student olivia = createStudentUseCase.createStudent(new CreateStudentCommand(
                "Olivia", "Brown", "olivia.brown@student.com"
        ));
        Student noah = createStudentUseCase.createStudent(new CreateStudentCommand(
                "Noah", "Davis", "noah.davis@student.com"
        ));
        log.info("DataSeeder: created 4 students.");

        // ── 3. Courses ──────────────────────────────────────────────────────────
        // 6 courses are created so that 4 prerequisite relationships can be established.
        Course introProgramming = createCourseUseCase.createCourse(new CreateCourseCommand(
                alice.id(),
                "Introduction to Programming",
                "A beginner-friendly course covering the fundamentals of programming: variables, "
                        + "control flow, functions, and data structures.",
                BigDecimal.ZERO,
                CourseCategory.PROGRAMMING,
                CourseDifficulty.BEGINNER,
                8,
                Set.of("fundamentals", "variables", "loops")
        ));

        Course springFundamentals = createCourseUseCase.createCourse(new CreateCourseCommand(
                alice.id(),
                "Spring Boot Fundamentals",
                "Learn to build production-ready REST APIs with Spring Boot, covering dependency "
                        + "injection, JPA, validation, and exception handling.",
                new BigDecimal("29.99"),
                CourseCategory.WEB_DEVELOPMENT,
                CourseDifficulty.BEGINNER,
                12,
                Set.of("spring", "java", "rest-api")
        ));

        Course advancedSpring = createCourseUseCase.createCourse(new CreateCourseCommand(
                alice.id(),
                "Advanced Spring Boot",
                "Deep dive into microservices, Spring Security, Testcontainers, and "
                        + "distributed system patterns.",
                new BigDecimal("49.99"),
                CourseCategory.WEB_DEVELOPMENT,
                CourseDifficulty.ADVANCED,
                20,
                Set.of("microservices", "security", "testing")
        ));

        Course mlBasics = createCourseUseCase.createCourse(new CreateCourseCommand(
                robert.id(),
                "Machine Learning Basics",
                "An accessible introduction to ML concepts: supervised vs unsupervised learning, "
                        + "model evaluation, and scikit-learn workflows.",
                new BigDecimal("39.99"),
                CourseCategory.DATA_SCIENCE,
                CourseDifficulty.BEGINNER,
                14,
                Set.of("python", "statistics", "sklearn")
        ));

        Course deepLearning = createCourseUseCase.createCourse(new CreateCourseCommand(
                robert.id(),
                "Deep Learning with Python",
                "Build and train neural networks using TensorFlow and Keras. Covers CNNs, "
                        + "RNNs, and transfer learning.",
                new BigDecimal("59.99"),
                CourseCategory.DATA_SCIENCE,
                CourseDifficulty.ADVANCED,
                24,
                Set.of("neural-networks", "tensorflow", "keras")
        ));

        Course ethicalHacking = createCourseUseCase.createCourse(new CreateCourseCommand(
                sarah.id(),
                "Ethical Hacking Fundamentals",
                "Understand the hacker mindset: reconnaissance, exploitation, and remediation "
                        + "using Kali Linux tools.",
                new BigDecimal("44.99"),
                CourseCategory.CYBERSECURITY,
                CourseDifficulty.INTERMEDIATE,
                16,
                Set.of("penetration-testing", "kali", "nmap")
        ));
        log.info("DataSeeder: created 6 courses.");

        // Capture UUIDs for relationship setup
        UUID introProgrammingId = introProgramming.id();
        UUID springFundamentalsId = springFundamentals.id();
        UUID advancedSpringId = advancedSpring.id();
        UUID mlBasicsId = mlBasics.id();
        UUID deepLearningId = deepLearning.id();
        UUID ethicalHackingId = ethicalHacking.id();

        // ── 4. Student Profiles ─────────────────────────────────────────────────
        createProfileUseCase.createProfile(new CreateProfileCommand(
                emma.id(),
                "Junior developer passionate about backend systems and clean architecture.",
                "https://linkedin.com/in/emma-thompson",
                "+1-415-555-0101",
                "https://github.com/emmathompson",
                "https://emmathompson.dev",
                null,
                Set.of("Java", "Spring Boot", "SQL")
        ));
        createProfileUseCase.createProfile(new CreateProfileCommand(
                liam.id(),
                "Self-taught programmer transitioning from finance into software engineering.",
                "https://linkedin.com/in/liam-wilson",
                "+1-212-555-0202",
                "https://github.com/liamwilson",
                null,
                null,
                Set.of("Java", "Python", "Git")
        ));
        createProfileUseCase.createProfile(new CreateProfileCommand(
                olivia.id(),
                "Data enthusiast with a statistics degree, learning machine learning in practice.",
                "https://linkedin.com/in/olivia-brown",
                "+1-512-555-0303",
                "https://github.com/oliviabrown",
                "https://oliviabrown.io",
                null,
                Set.of("Python", "Statistics", "Pandas")
        ));
        createProfileUseCase.createProfile(new CreateProfileCommand(
                noah.id(),
                "CS student interested in systems programming and open-source contributions.",
                "https://linkedin.com/in/noah-davis",
                "+1-206-555-0404",
                "https://github.com/noahdavis",
                null,
                null,
                Set.of("C++", "Java", "Linux")
        ));
        log.info("DataSeeder: created 4 student profiles.");

        // ── 5. Course Prerequisites (4 rows — demonstrates @EmbeddedId composite PK) ──
        // Chain: Intro → Spring Fundamentals → Advanced Spring
        // Chain: Intro → ML Basics → Deep Learning
        manageCoursePrerequisiteUseCase.addPrerequisite(springFundamentalsId, introProgrammingId);
        manageCoursePrerequisiteUseCase.addPrerequisite(advancedSpringId, springFundamentalsId);
        manageCoursePrerequisiteUseCase.addPrerequisite(mlBasicsId, introProgrammingId);
        manageCoursePrerequisiteUseCase.addPrerequisite(deepLearningId, mlBasicsId);
        log.info("DataSeeder: created 4 course prerequisites.");

        // ── 6. Tags (demonstrates @ManyToMany @JoinTable — tags are shared entities) ──
        manageCourseTagUseCase.addTag(springFundamentalsId, "java");
        manageCourseTagUseCase.addTag(springFundamentalsId, "spring");
        manageCourseTagUseCase.addTag(springFundamentalsId, "web");
        manageCourseTagUseCase.addTag(advancedSpringId, "java");
        manageCourseTagUseCase.addTag(advancedSpringId, "spring");
        manageCourseTagUseCase.addTag(mlBasicsId, "python");
        manageCourseTagUseCase.addTag(mlBasicsId, "machine-learning");
        manageCourseTagUseCase.addTag(deepLearningId, "python");
        manageCourseTagUseCase.addTag(deepLearningId, "machine-learning");
        manageCourseTagUseCase.addTag(ethicalHackingId, "cybersecurity");
        log.info("DataSeeder: assigned tags to courses (6 unique tag entities reused across courses).");

        // ── 7. Lessons (2 VIDEO + 2 TEXT — demonstrates @Inheritance JOINED) ────
        createLessonUseCase.createLesson(new CreateLessonCommand(
                springFundamentalsId,
                "Getting Started with Spring Boot",
                1,
                LessonType.VIDEO,
                "https://cdn.skilltrack.com/videos/spring-boot-getting-started.mp4",
                900,
                null
        ));
        createLessonUseCase.createLesson(new CreateLessonCommand(
                springFundamentalsId,
                "Building Your First REST API",
                2,
                LessonType.VIDEO,
                "https://cdn.skilltrack.com/videos/spring-boot-first-rest-api.mp4",
                1200,
                null
        ));
        createLessonUseCase.createLesson(new CreateLessonCommand(
                mlBasicsId,
                "What is Machine Learning?",
                1,
                LessonType.TEXT,
                null,
                null,
                "# What is Machine Learning?\n\nMachine learning (ML) is a branch of artificial "
                        + "intelligence that enables systems to learn from data and improve over time "
                        + "without being explicitly programmed.\n\n## Key Concepts\n\n- **Supervised "
                        + "learning**: training on labelled examples\n- **Unsupervised learning**: "
                        + "finding hidden structure in unlabelled data\n- **Reinforcement learning**: "
                        + "learning via rewards and penalties"
        ));
        createLessonUseCase.createLesson(new CreateLessonCommand(
                mlBasicsId,
                "Understanding Linear Regression",
                2,
                LessonType.TEXT,
                null,
                null,
                "# Understanding Linear Regression\n\nLinear regression models the relationship "
                        + "between a dependent variable **y** and one or more independent variables "
                        + "**x** by fitting a straight line: `y = mx + b`.\n\n## When to Use It\n\n"
                        + "- Predicting continuous values (e.g. house prices, stock returns)\n"
                        + "- Understanding variable importance\n- Baseline model before trying "
                        + "more complex algorithms"
        ));
        log.info("DataSeeder: created 4 lessons (2 VIDEO + 2 TEXT).");

        // ── 8. Enrollments ──────────────────────────────────────────────────────
        enrollStudentUseCase.enrollStudent(emma.id(), introProgrammingId);
        enrollStudentUseCase.enrollStudent(liam.id(), springFundamentalsId);
        enrollStudentUseCase.enrollStudent(olivia.id(), mlBasicsId);
        enrollStudentUseCase.enrollStudent(noah.id(), introProgrammingId);
        log.info("DataSeeder: created 4 enrollments.");

        // ── 9. Reviews (student must be enrolled in the course first) ───────────
        createReviewUseCase.createReview(new CreateReviewCommand(
                emma.id(), introProgrammingId, 4,
                "Great starting point for beginners! Clear explanations and well-paced content."
        ));
        createReviewUseCase.createReview(new CreateReviewCommand(
                liam.id(), springFundamentalsId, 5,
                "Excellent course — very well structured and immediately applicable on the job."
        ));
        createReviewUseCase.createReview(new CreateReviewCommand(
                olivia.id(), mlBasicsId, 4,
                "Clear explanations with practical examples. Would love more exercises though."
        ));
        createReviewUseCase.createReview(new CreateReviewCommand(
                noah.id(), introProgrammingId, 5,
                "Best intro programming course I've taken. Highly recommend to absolute beginners."
        ));
        log.info("DataSeeder: created 4 reviews.");

        log.info("DataSeeder: seed complete — {} instructors, {} students, {} courses, "
                        + "{} prerequisites, {} lessons, {} enrollments, {} reviews loaded.",
                4, 4, 6, 4, 4, 4, 4);
    }
}
