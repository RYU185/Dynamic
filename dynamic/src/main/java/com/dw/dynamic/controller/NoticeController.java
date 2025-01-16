package com.dw.dynamic.controller;

import com.dw.dynamic.model.Notice;
import com.dw.dynamic.service.NoticeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notice")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    @GetMapping("/all")
    public ResponseEntity<List<Notice>> getAllNotices(){
        return new ResponseEntity<>(
                noticeService.getAllNotices(),
                HttpStatus.OK
        );
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Notice>> getNoticesByTitle(@PathVariable String title){
        return new ResponseEntity<>(
                noticeService.getNoticesByTitle(title),
                HttpStatus.OK
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Notice> getNoticeById(@PathVariable Long id){
        return new ResponseEntity<>(
                noticeService.getNoticeById(id),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteNotice(@PathVariable Long id, HttpServletRequest request){
        return new ResponseEntity<>(
                noticeService.deleteNotice(id,request),
                HttpStatus.OK
        );
    }
    @PostMapping("/save")
    public ResponseEntity<Notice> saveNotice(@RequestBody Notice notice,HttpServletRequest request){
        return new ResponseEntity<>(
                noticeService.saveNotice(notice,request),
                HttpStatus.OK
        );
    }
}
