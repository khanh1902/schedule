package com.laptrinhjava.LichHoc.controller;

import com.laptrinhjava.LichHoc.entity.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/test")
public class TestController {

    @GetMapping("")
    ResponseEntity<ResponseObject> getAdmin(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Ok", "Dang nhap thanh cong", "Day la trang admin")
        );
    }

}
