INSERT INTO authority (authority_name) VALUES ('ADMIN');
INSERT INTO authority (authority_name) VALUES ('USER');
INSERT INTO authority (authority_name) VALUES ('NON-USER');

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

INSERT INTO guide (title,text)
VALUES
('무료 급여명세서 이용 가이드','메인 화면에 있는 무료 급여명세서 상에 근무 날짜 , 근무 시간, 시급을 작성 후 계산하기 버튼을 눌러주세요') ;

INSERT INTO formation_data (title)
VALUES
('급여명세서 양식');



INSERT INTO category (name) VALUES('강의');
INSERT INTO category (name) VALUES('정기 구독권');

INSERT INTO product (id,price,category_name) VALUES('C1',15000,'강의');
INSERT INTO product (id,price,category_name) VALUES('S1',30000,'정기 구독권');

INSERT INTO payroll_subscription(title,start_date,expire_date,id) VALUES('3개월 구독권','2025-01-13','2025-04-12','S1');
INSERT INTO course(title,add_date,description,id) VALUES('급여계산 및 공제','2025-01-13','급여 계산 방법 및  공제 방법에 대한 설명','C1');

INSERT INTO review(text,rating,add_date,modified_date,user_name,product_id,is_active) VALUES('이용하기 편해요', 5 , '2025-01-13','2025-01-13','sangsu1234','S1',true);
INSERT INTO board(title,answer,add_date,modify_date,is_active,user_name) VALUES('급여명세서 양식은 어떻게 작성하나요 ?', false,'2025-01-13','2025-01-16',true,'sangsu1234');
INSERT INTO comment(board_id,text,add_date,is_active,user_name) VALUES(1,'저도 궁금해요','2025-01-13',true,'sangsu1234');

INSERT INTO cart(user_name,product_id,is_active) VALUES ('sangsu1234','S1',true);

INSERT INTO deduction_and_tax(name, amount) VALUES('건강보험',3.545);
INSERT INTO deduction_and_tax(name, amount) VALUES('국민연금',4.5);
INSERT INTO deduction_and_tax(name, amount) VALUES('장기요양',12.95);
INSERT INTO deduction_and_tax(name, amount) VALUES('고용보험',0.09);
INSERT INTO deduction_and_tax(name, amount) VALUES('소득세',0);
INSERT INTO deduction_and_tax(name, amount) VALUES('지방세',0);

INSERT INTO freelancer(name, amount) VALUES('3.3%',3.3);
INSERT INTO freelancer(name, amount) VALUES('not freelancer',0);



INSERT INTO payroll_template(start_payroll_period,last_payroll_period,payment_date,salary,bonus,meal_allowance,transport_allowance,other_allowance,freelancer,is_active)
VALUES ('2025-01-13','2025-02-12','2025-02-12',2500000,100000,100000,0,0,'not freelancer',true);

INSERT INTO template_detail(payroll_template,deduction_and_taxname)
VALUES(1,'건강보험'),(1,'국민연금'),(1,'장기요양'),(1,'고용보험'),(1,'소득세'),(1,'지방세');

INSERT INTO employee (name, department,position,hire_date, phone_number, user_name,payroll_template,is_active,free_template)
VALUES
('김철수', '없음', '직원', '2025-01-13','010-1234-5678', 'sangsu1234',1,true,false);

INSERT INTO purchase_history(product_id,user_name,price,purchase_date) VALUES('S1','sangsu1234',15000,'2025-01-13');

INSERT INTO user_product(user_name,product_id) VALUES('sangsu1234','S1');
