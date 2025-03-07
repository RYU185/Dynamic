INSERT INTO authority(authority_name) VALUES ('ADMIN');
INSERT INTO authority(authority_name) VALUES ('USER');
INSERT INTO authority(authority_name) VALUES ('NON-USER');

 INSERT INTO user(user_name, company_name, real_name, password, gender, email,phone_number, business_number,business_type, exist_business_operator, point, user_authority)
    VALUES
        ('admin', '없음','관리자', '$2b$12$A0kgVpplgbH3ZZ1E89441eacUXljTTt7nP8I3RdLtW0P6/CXdEnCm', 'MALE','admin@gmail.com', '010-1111-1111', '없음','없음',false , 0, 'ADMIN');

INSERT INTO user (user_name, company_name, real_name, password, gender, email,phone_number, business_number,business_type, exist_business_operator, point, user_authority)
VALUES
    ('sangsu1234', '좋은상사', '이상수', '$2b$12$A0kgVpplgbH3ZZ1E89441eacUXljTTt7nP8I3RdLtW0P6/CXdEnCm', 'MALE','sangsu@gmail.com', '010-9123-4567', '123-45-67890','도소매업', true, 0, 'USER');
INSERT INTO user (user_name, company_name, real_name, password, gender, email,phone_number, business_number,business_type, exist_business_operator, point, user_authority)
VALUES
    ('dynamic', '없음', '다이나믹', '$2b$12$A0kgVpplgbH3ZZ1E89441eacUXljTTt7nP8I3RdLtW0P6/CXdEnCm', 'MALE','dynamic@gmail.com', '010-1234-4567', '없음','없음', false, 0, 'USER');


INSERT INTO notice (notice_title, text, add_date,modified_date)
VALUES
( '안녕하세요. 회원님의 첫 가입을 축하드립니다!', '안녕하세요. 저희 사이트에 가입해주셔서 감사합니다. 저희 사이트에서 무엇보다 편리하고 다양한 서비스를 즐기시길 바랍니다.', '2025-01-13','2025-01-13');
INSERT INTO notice (notice_title, text, add_date,modified_date)
VALUES
( '서비스 점검(3.5 목. 2:00~08:00)', '점검이 진행되는 시간 동안  게시글/댓글 읽기 기능은 이용하실 수 있으나,게시글/댓글의 쓰기/수정/삭제 등의 작업이 제한됩니다. 이용해주시는 여러분께 감사합니다', '2025-01-13','2025-01-13');
INSERT INTO notice (notice_title, text, add_date,modified_date)
VALUES
( '로그인 기능 오류 발생 안내 (3.5 목)', '일부 간헐적인 로그인 오류가 복구되었습니다.현재 정상적으로 서비스를 이용하실 수 있습니다.​', '2025-01-13','2025-01-13');





INSERT INTO guide (title,text,add_date)
VALUES
('무료 급여명세서 이용 가이드','메인 화면에 있는 무료 급여명세서 상에 근무 날짜 , 근무 시간, 시급을 작성 후 계산하기 버튼을 눌러주세요','2025-02-25') ;
INSERT INTO guide (title,text,add_date)
VALUES
('서비스 이용 가이드','사업자 유무에 따른 회원가입 후 사업자 오픈 대비 강의 시청 혹은 사업자 개시 후 정기구독권 서비스 이용을 하실 수 있습니다','2025-03-05') ;
INSERT INTO guide (title,text,add_date)
VALUES
('정기 구독권 이용 가이드','사업자가 있는 대상 한에서 이용이 가능하고, 구매 시 매달 급여 자동 계산이 되는 서비스를 이용할 수 있고 직원 등록을 하여 해당 직원에 맞게 급여명세서를 작성하실 수 있습니다','2025-03-05') ;


INSERT INTO formation_data (title,add_date)
VALUES
('급여명세서 양식','2025-02-25');
INSERT INTO formation_data (title,add_date)
VALUES
('근로계약서 양식','2025-03-05');
INSERT INTO formation_data (title,add_date)
VALUES
('사업자등록신청 양식','2025-03-05');


INSERT INTO category (name) VALUES('강의');
INSERT INTO category (name) VALUES('정기 구독권');

INSERT INTO product (id,price,category_name,is_active) VALUES('C1',15000,'강의',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('C2',17000,'강의',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('C3',15000,'강의',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('C4',17000,'강의',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('C5',17000,'강의',true);
INSERT INTO product (id,price,category_name,is_active) VALUES('C6',17000,'강의',true);



INSERT INTO course(title,add_date,description,id) VALUES('급여계산 및 공제','2025-01-13','급여 계산 방법 및 공제 방법에 대한 설명','C1');
INSERT INTO course(title,add_date,description,id) VALUES('사업자 등록 방법','2025-01-13','사업자 등록 방법에 대한 설명','C2');
INSERT INTO course(title,add_date,description,id) VALUES('근로계약서 작성 방법','2025-01-13','근로계약서 직원, 알바생별 작성 방법','C3');
INSERT INTO course(title,add_date,description,id) VALUES('원활한 매출 관리에 비법','2025-01-13','원활한 매출 관리에 대한 비법을 상세하게 다루고 있습니다. 한정된 업종인(카페업, 음식, 도소매업)','C4');
INSERT INTO course(title,add_date,description,id) VALUES('신고기간에 대한 설명','2025-01-13','면세, 법인, 간이, 개인사업자들의 필수 항목인 신고기간에 대한 설명','C5');
INSERT INTO course(title,add_date,description,id) VALUES('세금의 최소화를 위한 방법','2025-01-13','매년 최소 2번 이상 납부하는 세금에 대한 부담을 완하하기 위한 필수 시청 강의','C6');

INSERT INTO board(title,answer,add_date,modify_date,is_active,user_name) VALUES('급여명세서 양식은 어떻게 작성하나요 ?', false,'2025-01-13','2025-01-16',true,'sangsu1234');
INSERT INTO board(title,answer,add_date,modify_date,is_active,user_name) VALUES('직원 등록이 원활하게 이뤄지지 않는 것같은데 어떻게 해야될까요?', false,'2025-01-13','2025-01-16',true,'sangsu1234');
INSERT INTO board(title,answer,add_date,modify_date,is_active,user_name) VALUES('간편하게 이용할 수 있는 사이트인 것 같습니다', false,'2025-01-13','2025-01-16',false,'dynamic');
INSERT INTO comment(board_id,text,add_date,is_active,user_name) VALUES(1,'저도 궁금해요','2025-01-13',true,'dynamic');
INSERT INTO comment(board_id,text,add_date,is_active,user_name) VALUES(1,'이용 가이드를 확인하시면 상세한 설명이 되어있어 참조 부탁드립니다.','2025-01-15',true,'admin');
INSERT INTO comment(board_id,text,add_date,is_active,user_name) VALUES(3,'저도요~ 자주 이용할 것같습니다','2025-01-13',true,'sangsu1234');

--INSERT INTO cart(user_name,product_id,is_active) VALUES ('sangsu1234','S1',true);
--INSERT INTO cart(user_name,product_id,is_active) VALUES ('sangsu1234','C1',true);
--INSERT INTO cart(user_name,product_id,is_active) VALUES ('sangsu1234','S2',true);
--INSERT INTO cart(user_name,product_id,is_active) VALUES ('sangsu1234','C2',false);

INSERT INTO deduction_and_tax(name, amount) VALUES('건강보험',3.545);
INSERT INTO deduction_and_tax(name, amount) VALUES('국민연금',4.5);
INSERT INTO deduction_and_tax(name, amount) VALUES('장기요양',12.95);
INSERT INTO deduction_and_tax(name, amount) VALUES('고용보험',0.09);
INSERT INTO deduction_and_tax(name, amount) VALUES('소득세',0);
INSERT INTO deduction_and_tax(name, amount) VALUES('지방세',0);

INSERT INTO freelancer(name, amount) VALUES('3.3%',3.3);
INSERT INTO freelancer(name, amount) VALUES('not freelancer',0);

--INSERT INTO employee (name, department,position,hourly_rate,birthday,hire_date, phone_number, user_name,is_active,free_template) VALUES
--('김철수', '없음', '직원',10030 ,'2025-01-13','1998-02-13','010-1234-5678', 'sangsu1234',true,false);
--INSERT INTO payroll_template(start_payroll_period,last_payroll_period,payment_date,is_active,employee_id,total_amount,final_payment)
--VALUES ('2025-01-13','2025-02-12','2025-02-12',true,1,2000000,1980000);
--INSERT INTO employee (name, department,position,hourly_rate,birthday,hire_date, phone_number, user_name,is_active,free_template) VALUES
--('박종수', '없음', '직원',10030 ,'2025-01-13','1998-02-13','010-1234-2222', 'sangsu1234',true,false);
--INSERT INTO payroll_template(start_payroll_period,last_payroll_period,payment_date,is_active,employee_id,total_amount,final_payment)
--VALUES ('2025-01-13','2025-02-12','2025-02-12',true,2,1500000,1470000);


--
--INSERT INTO employee (name, department,position,hourly_rate,birthday,hire_date, phone_number, user_name,is_active,free_template)VALUES
--('홍길동', '없음', '직원',10030 ,'2025-01-13','1998-02-13','010-1234-1111', 'admin',true,false);
--INSERT INTO payroll_template(start_payroll_period,last_payroll_period,payment_date,is_active,employee_id,total_amount,final_payment)
--VALUES ('2025-01-13','2025-02-12','2025-02-12',true,3,3000000,2860000);
--
--
--INSERT INTO purchase_history(product_id,user_name,price,purchase_date) VALUES('S1','sangsu1234',20000,'2025-01-13');
--INSERT INTO purchase_history(product_id,user_name,price,purchase_date) VALUES('C2','sangsu1234',17000,'2025-01-22');
--
--INSERT INTO user_product(user_name,product_id) VALUES('sangsu1234','S1');
--INSERT INTO user_product(user_name,product_id) VALUES('sangsu1234','C2');
--
--INSERT INTO point(amount,add_date,purchase_history_id) VALUES (1000,'2025-01-13',1);
--INSERT INTO point(amount,add_date,purchase_history_id) VALUES (850,'2025-01-22',2);