USE gallagher;

-- Dropping Table Group_Evaluations
DROP TABLE group_evaluations;
-- Dropping Table Member_Evaluations
DROP TABLE member_evaluations;
-- Dropping Table Evaluation_Stages
DROP TABLE evaluation_stages;
-- Dropping Table Group_Members
DROP TABLE group_members;
-- Dropping Table Groups
DROP TABLE groups;
-- Dropping Table Users
DROP TABLE users;
-- Dropping Table Courses
DROP TABLE courses;

-- Create Table courses
CREATE TABLE courses (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	code VARCHAR(7),
	title VARCHAR(50),
	term VARCHAR(7),
	`year` SMALLINT UNSIGNED,
	created_on TIMESTAMP,
	CONSTRAINT pk_course PRIMARY KEY (id)
) ENGINE=InnoDB;
DESCRIBE courses;

-- Create Table groups
CREATE TABLE groups (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	course_id INT UNSIGNED NOT NULL,
	created_on TIMESTAMP,
	CONSTRAINT pk_groups PRIMARY KEY(id),
	CONSTRAINT fk_groups_course FOREIGN KEY (course_id)
      REFERENCES courses(id)
      ON UPDATE CASCADE ON DELETE CASCADE
) ENGINE=InnoDB;
DESCRIBE groups;

-- Create Table users
CREATE TABLE users (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	email VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	need_password_reset BOOLEAN NOT NULL,
	fullname VARCHAR(100) NOT NULL,
	is_inspector BOOLEAN NOT NULL,
	created_on TIMESTAMP,
	CONSTRAINT pk_users PRIMARY KEY(id),
	CONSTRAINT uk_users_email UNIQUE (email)
) ENGINE=InnoDB;
DESCRIBE users;

-- Create Table group_members
CREATE TABLE group_members (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	group_id INT UNSIGNED NOT NULL,
	user_id INT UNSIGNED NOT NULL,
	created_on TIMESTAMP,
	CONSTRAINT pk_group_members PRIMARY KEY(id),
	CONSTRAINT fk_group_members_groups FOREIGN KEY (group_id)
      REFERENCES groups(id)
      ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_group_members_users FOREIGN KEY (user_id)
      REFERENCES users(id)
      ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT uk_group_members UNIQUE (group_id, user_id)
) ENGINE=InnoDB;
DESCRIBE group_members;

-- Create Table evaluation_stages
CREATE TABLE evaluation_stages (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	course_id INT UNSIGNED NOT NULL,
	name VARCHAR(100) NOT NULL,
	description VARCHAR(500),
	start_date DATE NOT NULL,
	end_date DATE NOT NULL,
	is_open BOOLEAN NOT NULL,
	created_on TIMESTAMP,
	CONSTRAINT pk_evaluation_stages PRIMARY KEY(id),
	CONSTRAINT fk_evaluation_stages_courses FOREIGN KEY (course_id)
      REFERENCES courses(id)
      ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT uk_evaluation_stages UNIQUE (start_date, course_id)
) ENGINE=InnoDB;
DESCRIBE evaluation_stages;

-- Create Table member_evaluations
CREATE TABLE member_evaluations (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	evaluation_stage_id INT UNSIGNED NOT NULL,
	group_id INT UNSIGNED NOT NULL,
	evaluator_id INT UNSIGNED NOT NULL,
	evaluatee_id INT UNSIGNED NOT NULL,
	responsibilities VARCHAR(500) NOT NULL,
	task_completeness ENUM('yes','no','most of them') NOT NULL,
	task_completeness_details VARCHAR(500),
	participation ENUM('1','2','3','4') NOT NULL,
	participation_details VARCHAR(500),
	grade ENUM('A','B','C','D','F') NOT NULL,
	percentage INT UNSIGNED,
	created_on TIMESTAMP,
	CONSTRAINT pk_member_evaluations PRIMARY KEY(id),
	CONSTRAINT fk_member_evaluations_stages FOREIGN KEY (evaluation_stage_id)
      REFERENCES evaluation_stages(id)
      ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_member_evaluations_groups FOREIGN KEY (group_id)
      REFERENCES groups(id)
      ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_member_evaluations_users_evaluator FOREIGN KEY (evaluator_id)
      REFERENCES users(id)
      ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_member_evaluations_users_evaluatee FOREIGN KEY (evaluatee_id)
      REFERENCES users(id)
      ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT uk_member_evaluations
	  UNIQUE (evaluation_stage_id, group_id, evaluator_id, evaluatee_id)
) ENGINE=InnoDB;
DESCRIBE member_evaluations;

-- Create Table group_evaluation
CREATE TABLE group_evaluations (
	id INT UNSIGNED NOT NULL AUTO_INCREMENT,
	evaluation_stage_id INT UNSIGNED NOT NULL,
	group_id INT UNSIGNED NOT NULL,
	evaluator_id INT UNSIGNED NOT NULL,
	goals ENUM('1','2','3','4','5') NOT NULL,
	goals_details VARCHAR(500),
	openness ENUM('1','2','3','4','5') NOT NULL,
	openness_details VARCHAR(500),
	mutal_trust ENUM('1','2','3','4','5') NOT NULL,
	mutal_trust_details VARCHAR(500),
	difference_attitude ENUM('1','2','3','4','5') NOT NULL,
	difference_attitude_details VARCHAR(500),
	support ENUM('1','2','3','4','5') NOT NULL,
	support_details VARCHAR(500),
	participation ENUM('1','2','3','4','5') NOT NULL,
	participation_details VARCHAR(500),
	decision_making ENUM('1','2','3','4','5') NOT NULL,
	decision_making_details VARCHAR(500),
	flexibility ENUM('1','2','3','4','5') NOT NULL,
	flexibility_details VARCHAR(500),
	resources_use ENUM('1','2','3','4','5') NOT NULL,
	resources_use_details VARCHAR(500),
	grade ENUM('A','B','C','D','F') NOT NULL,
	percentage INT UNSIGNED,
	created_on TIMESTAMP,
	CONSTRAINT pk_group_evaluations PRIMARY KEY(id),
	CONSTRAINT fk_group_evaluations_stages FOREIGN KEY (evaluation_stage_id)
      REFERENCES evaluation_stages(id)
      ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_group_evaluations_groups FOREIGN KEY (group_id)
      REFERENCES groups(id)
      ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_group_evaluations_users_evaluator FOREIGN KEY (evaluator_id)
      REFERENCES users(id)
      ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT uk_group_evaluations
	  UNIQUE (evaluation_stage_id, group_id, evaluator_id)
) ENGINE=InnoDB;
DESCRIBE group_evaluations;

-- Insert one record in courses table
INSERT INTO courses (code, title, term, `year`)
  VALUES ('SWE5002','Software Engineering 2','Spring',2015);

COMMIT;   

SHOW TABLES;
