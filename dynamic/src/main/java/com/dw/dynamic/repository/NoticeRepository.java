package com.dw.dynamic.repository;

import com.dw.dynamic.model.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
    public List<Notice> findByNoticeTitle(String title);
}
