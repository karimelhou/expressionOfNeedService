package ma.fstt.controller;


import ma.fstt.common.messages.BaseResponse;
import ma.fstt.dto.ExpressionOfNeedDTO;
import ma.fstt.service.ExpressionOfNeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@Validated
@RestController
@RequestMapping("/needs")
public class ExpressionOfNeedController {
    @Autowired
    private ExpressionOfNeedService expressionOfNeedService;

    @GetMapping
    public ResponseEntity<List<ExpressionOfNeedDTO>> getAllExpressionOfNeed() {
        List<ExpressionOfNeedDTO> list = expressionOfNeedService.findExpressionOfNeedList();
        return new ResponseEntity<List<ExpressionOfNeedDTO>>(list, HttpStatus.OK);
    }

    @PostMapping(value = { "/add" })
    public ResponseEntity<BaseResponse> createExpressionOfNeed(@RequestBody ExpressionOfNeedDTO userDTO) {
        BaseResponse response = expressionOfNeedService.createOrUpdateExpressionOfNeed(userDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<ExpressionOfNeedDTO>> getAllExpressionOfNeedByUserId(@PathVariable Long id) {
        List<ExpressionOfNeedDTO> list = expressionOfNeedService.findExpressionOfNeedByUserId(id);
        return new ResponseEntity<List<ExpressionOfNeedDTO>>(list, HttpStatus.OK);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<BaseResponse> updateExpressionOfNeed(
            @PathVariable("id") Long id,
            @RequestBody ExpressionOfNeedDTO updatedExpressionOfNeedDTO) {

        BaseResponse response = expressionOfNeedService.updateExpressionOfNeed(id, updatedExpressionOfNeedDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<BaseResponse> deleteExpressionOfNeedById(@PathVariable Long id) {
        BaseResponse response = expressionOfNeedService.deleteExpressionOfNeed(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExpressionOfNeedDTO> getExpressionOfNeedById(@PathVariable Long id) {
        ExpressionOfNeedDTO list = expressionOfNeedService.findByExpressionOfNeedId(id);
        return new ResponseEntity<ExpressionOfNeedDTO>(list, HttpStatus.OK);
    }

}
