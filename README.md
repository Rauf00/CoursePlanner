# CMPT 213 Assignment 5


## How [DataParser](./src/main/java/ca/cmpt213/courseplanner/DataParser.java) works.

**At the end of parsing, DepartmentManager will have a list of Departments**

- each Department has a dictionary of courses   -> key: catalogNumber            val: Course object
- each Course has a dictionary of Offering      -> key: semesterCode+location    val: Offering object
- each Offering has a dictionary of Component   -> key: COMPONENT TYPE      val: [SumCapacity, SumEnrollment]


<br>

Here is how the algorithm works

- Read CSV one line at a time
- Each line is an Offering
- Get Department name
- If Department name is NOT in department dictionary: create a department and add it to dictionary
- Get Course CatalogNumber
- If Course CatalogNumber is NOT in course dictionary: create a new course and add it to dictionary
- If Course Does NOT have offering with Given semester code and location, create a new offering
- If the given Offering has a new instructor, append it to to instructor string. (EX: CMPT 123 LEC is taught by Brian Fraser and each of the 5 LAB is taught by a different instructor).
- Get the offering component
- If the offering component dictionary does NOT have the parsed component, add it to dictionary and increment capacity and enrollments.

