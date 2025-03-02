INSERT INTO authority(authority_name) VALUES ('ADMIN');
INSERT INTO authority(authority_name) VALUES ('USER');
INSERT INTO authority(authority_name) VALUES ('NON-USER');

 INSERT INTO user(user_name, company_name, real_name, password, gender, email,phone_number, business_number,business_type, exist_business_operator, point, user_authority)
    VALUES
        ('admin', '없음','관리자', '$2b$12$A0kgVpplgbH3ZZ1E89441eacUXljTTt7nP8I3RdLtW0P6/CXdEnCm', 'MALE','admin@gmail.com', '010-1111-1111', '없음','없음',false , 0, 'ADMIN');

INSERT INTO user (user_name, company_name, real_name, password, gender, email,phone_number, business_number,business_type, exist_business_operator, point, user_authority)
VALUES
    ('sangsu1234', '좋은상사', '이상수', '$2b$12$A0kgVpplgbH3ZZ1E89441eacUXljTTt7nP8I3RdLtW0P6/CXdEnCm', 'MALE','sangsu@gmail.com', '010-9123-4567', '123-45-67890','도소매업', true, 100, 'USER');
--

INSERT INTO notice (notice_title, text, add_date,modified_date)
VALUES
( '안녕하세요. 회원님의 첫 가입을 축하드립니다!', '안녕하세요. 저희 사이트에 가입해주셔서 감사합니다. 저희 사이트에서 무엇보다 편리하고 다양한 서비스를 즐기시길 바랍니다.', '2025-01-13','2025-01-14');

INSERT INTO guide (title,text,add_date)
VALUES
('무료 급여명세서 이용 가이드','메인 화면에 있는 무료 급여명세서 상에 근무 날짜 , 근무 시간, 시급을 작성 후 계산하기 버튼을 눌러주세요','2025-02-25') ;

INSERT INTO formation_data (title,add_date)
VALUES
('급여명세서 양식','2025-02-25');



INSERT INTO category (name) VALUES('강의');
INSERT INTO category (name) VALUES('정기 구독권');

INSERT INTO product (id,price,category_name,is_active) VALUES('C1',15000,'강의',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('C2',17000,'강의',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S1',20000,'정기 구독권',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S2',38000,'정기 구독권',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S3',55000,'정기 구독권',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S4',70000,'정기 구독권',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S5',90000,'정기 구독권',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S6',110000,'정기 구독권',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S7',125000,'정기 구독권',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S8',152000,'정기 구독권',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S9',164000,'정기 구독권',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S10',1850000,'정기 구독권',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S11',2020000,'정기 구독권',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('S12',213000,'정기 구독권',true);


INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('1개월 정기 구독권','2025-01-13','2025-04-12','S1');
INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('2개월 정기 구독권','2025-01-13','2025-07-12','S2');
INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('3개월 정기 구독권','2025-01-13','2025-07-12','S3');
INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('4개월 정기 구독권','2025-01-13','2025-07-12','S4');
INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('5개월 정기 구독권','2025-01-13','2025-07-12','S5');
INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('6개월 정기 구독권','2025-01-13','2025-07-12','S6');
INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('7개월 정기 구독권','2025-01-13','2025-07-12','S7');
INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('8개월 정기 구독권','2025-01-13','2025-07-12','S8');
INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('9개월 정기 구독권','2025-01-13','2025-07-12','S9');
INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('10개월 정기 구독권','2025-01-13','2025-07-12','S10');
INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('11개월 정기 구독권','2025-01-13','2025-07-12','S11');
INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('12개월 정기 구독권','2025-01-13','2025-07-12','S12');

INSERT INTO course(title,add_date,description,id) VALUES('급여계산 및 공제','2025-01-13','급여 계산 방법 및 공제 방법에 대한 설명','C1');
INSERT INTO course(title,add_date,description,id) VALUES('사업자 등록 방법','2025-01-13','사업자 등록 방법에 대한 설명','C2');

INSERT INTO review(text,rating,add_date,modified_date,user_name,product_id,is_active) VALUES('이용하기 편해요', 5 , '2025-01-13','2025-01-13','sangsu1234','S1',true);
INSERT INTO board(title,answer,add_date,modify_date,is_active,user_name) VALUES('급여명세서 양식은 어떻게 작성하나요 ?', false,'2025-01-13','2025-01-16',true,'sangsu1234');
INSERT INTO board(title,answer,add_date,modify_date,is_active,user_name) VALUES('그냥 안녕 ?', false,'2025-01-13','2025-01-16',false,'sangsu1234');
INSERT INTO comment(board_id,text,add_date,is_active,user_name) VALUES(1,'저도 궁금해요','2025-01-13',true,'sangsu1234');
INSERT INTO comment(board_id,text,add_date,is_active,user_name) VALUES(1,'저도 궁금해요','2025-01-13',true,'sangsu1234');
INSERT INTO comment(board_id,text,add_date,is_active,user_name) VALUES(1,'저도 궁금해요','2025-01-13',true,'admin');

INSERT INTO cart(user_name,product_id,is_active) VALUES ('sangsu1234','S1',true);
INSERT INTO cart(user_name,product_id,is_active) VALUES ('sangsu1234','C1',true);
INSERT INTO cart(user_name,product_id,is_active) VALUES ('sangsu1234','S2',true);
INSERT INTO cart(user_name,product_id,is_active) VALUES ('sangsu1234','C2',false);

INSERT INTO deduction_and_tax(name, amount) VALUES('건강보험',3.545);
INSERT INTO deduction_and_tax(name, amount) VALUES('국민연금',4.5);
INSERT INTO deduction_and_tax(name, amount) VALUES('장기요양',12.95);
INSERT INTO deduction_and_tax(name, amount) VALUES('고용보험',0.09);
INSERT INTO deduction_and_tax(name, amount) VALUES('소득세',0);
INSERT INTO deduction_and_tax(name, amount) VALUES('지방세',0);

INSERT INTO freelancer(name, amount) VALUES('3.3%',3.3);
INSERT INTO freelancer(name, amount) VALUES('not freelancer',0);

INSERT INTO employee (name, department,position,hourly_rate,birthday,hire_date, phone_number, user_name,is_active,free_template) VALUES
('김철수', '없음', '직원',10030 ,'2025-01-13','1998-02-13','010-1234-5678', 'sangsu1234',true,false);
INSERT INTO payroll_template(start_payroll_period,last_payroll_period,payment_date,is_active,employee_id)
VALUES ('2025-01-13','2025-02-12','2025-02-12',true,1);
INSERT INTO employee (name, department,position,hourly_rate,birthday,hire_date, phone_number, user_name,is_active,free_template) VALUES
('박종수', '없음', '직원',10030 ,'2025-01-13','1998-02-13','010-1234-2222', 'sangsu1234',true,false);
INSERT INTO payroll_template(start_payroll_period,last_payroll_period,payment_date,is_active,employee_id)
VALUES ('2025-01-13','2025-02-12','2025-02-12',true,2);


INSERT INTO employee (name, department,position,hourly_rate,birthday,hire_date, phone_number, user_name,is_active,free_template)VALUES
('홍길동', '없음', '직원',10030 ,'2025-01-13','1998-02-13','010-1234-1111', 'admin',true,false);
INSERT INTO payroll_template(start_payroll_period,last_payroll_period,payment_date,is_active,employee_id)
VALUES ('2025-01-13','2025-02-12','2025-02-12',true,3);


INSERT INTO purchase_history(product_id,user_name,price,purchase_date) VALUES('S1','sangsu1234',20000,'2025-01-13');
INSERT INTO purchase_history(product_id,user_name,price,purchase_date) VALUES('C2','sangsu1234',17000,'2025-01-22');

INSERT INTO user_product(user_name,product_id) VALUES('sangsu1234','S1');
INSERT INTO user_product(user_name,product_id) VALUES('sangsu1234','C2');
--
INSERT INTO point(amount,add_date,purchase_history_id) VALUES (20000,'2025-01-13',1)
INSERT INTO point(amount,add_date,purchase_history_id) VALUES (17000,'2025-01-22',2)