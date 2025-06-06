INSERT INTO response_metadata (response_code, message)
VALUES ('_0000', 'BAD REQUEST'),
       ('_0001', 'RUNTIME EXCEPTION'),
       ('_0002', 'Illegal argument exception'),
       ('_0003', 'Token expired exception'),
       ('_0004', 'Unauthorized exception'),
       ('_0005', 'Incorrect password of phone number'),
       ('_0006', 'Access denied exception'),
       ('_0007', 'Invalid compact JWT string'),

       ('_0100', 'Child not found'),
       ('_0101', 'Children not found'),
       ('_0102', 'Parent not found'),
       ('_0103', 'Course not found'),
       ('_0104', 'Education center not found'),
       ('_0105', 'Teacher not found'),

       ('_0200', 'User already exists'),

       ('_0300', 'Balance update error'),
       ('_0301', 'Insufficient funds'),

       ('_0400', 'Child does not belong to parent'),
       ('_0401', 'Child not enrolled to course'),
       ('_0402', 'Ineligible child'),

       ('_0800', 'Course is full'),


       ('_1000', 'Children retrieved successfully'),
       ('_1001', 'Child retrieved successfully'),
       ('_1002', 'Child updated successfully'),
       ('_1003', 'Child removed successfully'),
       ('_1004', 'Child''s courses retrieved successfully'),
       ('_1005', 'Child enrolled successfully'),
       ('_1006', 'Child''s unenrolled successfully'),
       ('_1007', 'Child created successfully'),

       ('_1300', 'Parent created successfully'),

       ('_1600', 'Education center created successfully'),
       ('_1601', 'Total revenue retrieved successfully'),
       ('_1602', 'Top courses by revenue retrieved successfully'),
       ('_1603', 'Children distribution by course retrieved successfully'),
       ('_1604', 'Monthly revenue data retrieved successfully'),
       ('_1605', 'Monthly children growth data retrieved successfully'),
       ('_1606', 'Average course duration retrieved successfully'),
       ('_1607', 'Average group fill percentage retrieved successfully'),
       ('_1608', 'Returning parents count retrieved successfully'),

       ('_1900', 'Course created successfully'),

       ('_2000', 'Group created successfully'),

       ('_2200', 'Teacher created successfully');
