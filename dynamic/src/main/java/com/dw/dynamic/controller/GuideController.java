package com.dw.dynamic.controller;

import com.dw.dynamic.model.Guide;
import com.dw.dynamic.service.GuideService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guide")
public class GuideController {

    @Autowired
    GuideService guideService;

    @GetMapping("/all")
    public ResponseEntity<List<Guide>> getAllGuides() {
        return new ResponseEntity<>(
                guideService.getAllGuides(),
                HttpStatus.OK);
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Guide> getGuideById(@PathVariable Long id) {
        return new ResponseEntity<>(
                guideService.getGuideById(id),
                HttpStatus.OK);
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Guide>> getGuidesByTitle(@PathVariable String title) {
        return new ResponseEntity<>(
                guideService.getGuidesByTitle(title),
                HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Guide> saveGuide(@RequestBody Guide guide, HttpServletRequest request){
        return new ResponseEntity<>(
                guideService.saveGuide(guide,request),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteGuide(@PathVariable Long id, HttpServletRequest request){
        return new ResponseEntity<>(
                guideService.deleteGuide(id,request),
                HttpStatus.OK
        );
    }
}
