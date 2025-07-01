package org.csu.healthsystem.controller;


import org.csu.healthsystem.pojo.VO.DataTagAssignRequestVO;
import org.csu.healthsystem.pojo.VO.DataTagCreateRequestVO;
import org.csu.healthsystem.pojo.VO.DataTagVO;
import org.csu.healthsystem.service.DataTagService;
import org.csu.healthsystem.common.CommonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/data/tags")
public class DataTagController {
    @Autowired
    private DataTagService dataTagService;

    @GetMapping
    public CommonResponse<List<DataTagVO>> getAllTags() {
        List<DataTagVO> tags = dataTagService.getAllTags();
        return CommonResponse.createForSuccess(tags);
    }
    @PostMapping
    public CommonResponse<Void> createTag(@RequestBody DataTagCreateRequestVO vo) {
        dataTagService.createTag(vo);
        return CommonResponse.createForSuccess(null);
    }
    @PostMapping("/assign")
    public CommonResponse<Void> assignTags(@RequestBody DataTagAssignRequestVO vo) {
        dataTagService.assignTags(vo);
        return CommonResponse.createForSuccess(null);
    }
}
