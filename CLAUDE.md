# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

### Build and Test
- `mvn clean compile` - Compile the project
- `mvn test` - Run all tests
- `mvn test -Dtest=ClassName` - Run specific test class
- `mvn test -Dtest=ClassName#methodName` - Run specific test method
- `mvn clean test jacoco:report` - Run tests and generate code coverage report

**Note**: Always run tests and lint before submitting changes.

### Quality Checks
- `mvn qulice:check` - Run Qulice quality checks (includes PMD, Checkstyle, etc.)
- `mvn checkstyle:check` - Run Checkstyle only

### Package
- `mvn package` - Build JAR artifact

## Architecture

This is an OOP-oriented Java library that provides Freemarker-based SQL template processing. 
Need use only best OOP practics and patterns.

### Core Components

**Query Processing Pipeline:**
- `Query` interface - Core abstraction for parsing Freemarker templates into SQL
- `StrQuery` - Parses templates from String input
- `FtlQuery` - Parses templates from classpath resources using ClassTemplateLoader

**Parameter System:**
- `Params` interface - Extends TemplateHashModel for Freemarker integration
- `FmParams` - Basic parameter container with list of name-value pairs
- `PageParams` - Decorator that adds pagination (page, size, orders) to existing params
- `CaseParams` - Decorator for conditional parameter inclusion
- `NullParams` - Empty parameter implementation

**Pagination Integration:**
- `Pageable` interface - Abstraction for pagination data
- `SpringPage` - Adapter for Spring Data's Pageable interface
- `Orderable` interface - For sorting functionality
- `SpringOrder` - Adapter for Spring Data's Sort.Order

### Design Patterns

The library follows strict OOP principles with:
- Decorator pattern for parameter composition (PageParams, CaseParams)
- Adapter pattern for Spring framework integration
- Interface segregation with focused responsibilities

### Testing

Tests use JUnit 5 and AssertJ. The `fake/` package contains test implementations that simulate real behavior without dependencies.

### Important Workflow Notes

- Always run tests, checkstyle and normalize comments before committing
- For checkstyle use mvn qulice:check
- Run tests and checkstyle after making significant changes to verify functionality
- Java version: 17.+
- Don't add "Generated with Claude Code" or "Co-Authored-By: Claude" to commit messages or PRs
- Do not include "Test plan" sections in PR descriptions
- Do not add comments that describe changes, progress, or historical modifications. Avoid comments like "new function," "added test," "now we changed this," or "previously used X, now using Y." Comments should only describe the current state and purpose of the code, not its history or evolution.
- After important functionality added, update README.md accordingly
- When merging master changes to an active branch, make sure both branches are pulled and up to date first
- Don't add "Test plan" section to PRs
- No empty line inside method
- Variable names must match pattern '^(id|[a-z]{3,12})$'
- All files should end with new line
- Copy license text from file [LICENSE.txt](LICENSE.txt)

### Git Workflow

- Commit should be not big.
- Split code style commits from refactoring commits from business logic from bug fixing commits.
- Git flow is trunk base styled
- Main branch named 'master'
- Do not push in origin repository
- Commit messages should allow to The Conventional Commits specification