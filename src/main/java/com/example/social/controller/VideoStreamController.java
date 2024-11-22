package com.example.social.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;

import com.example.social.service.VideoStreamService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/video")
public class VideoStreamController {

    private final VideoStreamService videoStreamService;

    public VideoStreamController(VideoStreamService videoStreamService) {
        this.videoStreamService = videoStreamService;
    }

    @GetMapping("/stream/{title}")
    public Mono<ResponseEntity<byte[]>> streamVideo( @RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @PathVariable("title") String title) {
        return Mono.just(videoStreamService.prepareContent(title,"mp4", httpRangeList));
    }
}
