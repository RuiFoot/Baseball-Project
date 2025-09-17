INSERT INTO positions (id, name_en, name_kr, category, code) VALUES
                                                                 (1, 'Pitcher', '투수', '배터리', 'P'),
                                                                 (2, 'Catcher', '포수', '배터리', 'C'),
                                                                 (3, 'First Baseman', '1루수', '내야수', '1B'),
                                                                 (4, 'Second Baseman', '2루수', '내야수', '2B'),
                                                                 (5, 'Third Baseman', '3루수', '내야수', '3B'),
                                                                 (6, 'Shortstop', '유격수', '내야수', 'SS'),
                                                                 (7, 'Left Fielder', '좌익수', '외야수', 'LF'),
                                                                 (8, 'Center Fielder', '중견수', '외야수', 'CF'),
                                                                 (9, 'Right Fielder', '우익수', '외야수', 'RF'),
                                                                 (10, 'Designated Hitter', '지명타자', '지명타자', 'DH'),
                                                                (101, 'Manager', '감독', '코칭스태프', 'MGR'),
                                                                (102, 'Coach', '코치', '코칭스태프', 'COACH'),
                                                                (103, 'Pitching Coach', '투수코치', '코칭스태프', 'PC'),
                                                                (104, 'Hitting Coach', '타격코치', '코칭스태프', 'HC')
ON CONFLICT (id) DO NOTHING;

-- teams 테이블 초기 데이터 삽입
INSERT INTO teams (id, name, founded_date) VALUES
    (1, 'Killer Whales', '2022-08-27')
ON CONFLICT (id) DO NOTHING;