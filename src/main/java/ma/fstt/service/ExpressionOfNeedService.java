package ma.fstt.service;




import jakarta.transaction.Transactional;
import ma.fstt.common.exceptions.RecordNotFoundException;
import ma.fstt.common.messages.BaseResponse;
import ma.fstt.common.messages.CustomMessage;
import ma.fstt.common.utils.Topic;
import ma.fstt.dto.ExpressionOfNeedDTO;
import ma.fstt.dto.UserDTO;
import ma.fstt.entity.ExpressionOfNeedEntity;
import ma.fstt.repo.ExpressionOfNeedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExpressionOfNeedService {
    @Autowired
    private ExpressionOfNeedRepo expressionOfNeedRepo;


    private final WebClient webClient;

    public ExpressionOfNeedService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8082/api/v1/auth/users").build();
    }

    public List<ExpressionOfNeedDTO> findExpressionOfNeedList() {
        return expressionOfNeedRepo.findAll().stream().map(this::copyExpressionOfNeedEntityToDto).collect(Collectors.toList());
    }

    public List<ExpressionOfNeedDTO> findExpressionOfNeedByUserId(Long id) {
        return expressionOfNeedRepo.findByUserId(id).stream().map(this::copyExpressionOfNeedEntityToDto).collect(Collectors.toList());
    }

    public ExpressionOfNeedDTO findByExpressionOfNeedId(Long expressionOfNeedId) {
        ExpressionOfNeedEntity userEntity = expressionOfNeedRepo.findById(expressionOfNeedId)
                .orElseThrow(() -> new RecordNotFoundException("ExpressionOfNeed id '" + expressionOfNeedId + "' does not exist !"));
        return copyExpressionOfNeedEntityToDto(userEntity);
    }

    public BaseResponse createOrUpdateExpressionOfNeed(ExpressionOfNeedDTO expressionOfNeedDTO) {
        // Vérifie d'abord l'existence de l'utilisateur avant de créer une expressionOfNeed
        Mono<UserDTO> userMono = webClient.get()
                .uri("/{id}", expressionOfNeedDTO.getUserId())
                .retrieve()
                .bodyToMono(UserDTO.class);

        // Attendez la réponse du service d'authentification
        UserDTO userResponse = userMono.block();

        if (userResponse != null && userResponse.getId() != null) {
            // L'utilisateur existe, continuez avec la création ou la mise à jour de l'expressionOfNeed
            ExpressionOfNeedEntity expressionOfNeedEntity = copyExpressionOfNeedDtoToEntity(expressionOfNeedDTO);
            expressionOfNeedRepo.save(expressionOfNeedEntity);
            return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
        } else {
            // L'utilisateur n'existe pas, vous pouvez gérer cela en lançant une exception, par exemple
            throw new RecordNotFoundException("L'utilisateur avec l'ID " + expressionOfNeedDTO.getUserId() + " n'existe pas.");
        }

    }

    public BaseResponse updateExpressionOfNeed(Long expressionOfNeedId, ExpressionOfNeedDTO updatedExpressionOfNeedDTO) {
        // Check if the expressionOfNeed with the given ID exists in the database
        if (!expressionOfNeedRepo.existsById(expressionOfNeedId)) {
            throw new RecordNotFoundException("ExpressionOfNeed id '" + expressionOfNeedId + "' does not exist!");
        }

        // Find the existing ExpressionOfNeedEntity by ID
        ExpressionOfNeedEntity existingExpressionOfNeedEntity = expressionOfNeedRepo.findById(expressionOfNeedId)
                .orElseThrow(() -> new RecordNotFoundException("ExpressionOfNeed id '" + expressionOfNeedId + "' does not exist !"));

        // Update the fields of the existing entity with the values from the updated DTO
        existingExpressionOfNeedEntity.setDescription(updatedExpressionOfNeedDTO.getDescription());
        existingExpressionOfNeedEntity.setUrgence(updatedExpressionOfNeedDTO.getUrgence());



        // Save the updated entity back to the database
        expressionOfNeedRepo.save(existingExpressionOfNeedEntity);
        return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());
    }

    public BaseResponse deleteExpressionOfNeed(Long expressionOfNeedId) {
        if (expressionOfNeedRepo.existsById(expressionOfNeedId)) {
            expressionOfNeedRepo.deleteById(expressionOfNeedId);
        } else {
            throw new RecordNotFoundException("No record found for given id: " + expressionOfNeedId);
        }
        return new BaseResponse(Topic.ASSISTANCE.getName() + CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
    }


    private ExpressionOfNeedDTO copyExpressionOfNeedEntityToDto(ExpressionOfNeedEntity expressionOfNeedEntity) {
        ExpressionOfNeedDTO expressionOfNeedDTO = new ExpressionOfNeedDTO();
        expressionOfNeedDTO.setExpressionOfNeedId(expressionOfNeedEntity.getExpressionOfNeedId());
        expressionOfNeedDTO.setDescription(expressionOfNeedEntity.getDescription());
        expressionOfNeedDTO.setUrgence(expressionOfNeedEntity.getUrgence());
        expressionOfNeedDTO.setUserId(expressionOfNeedEntity.getUserId());
        return expressionOfNeedDTO;
    }

    private ExpressionOfNeedEntity copyExpressionOfNeedDtoToEntity(ExpressionOfNeedDTO expressionOfNeedDTO) {
        ExpressionOfNeedEntity userEntity = new ExpressionOfNeedEntity();
        userEntity.setExpressionOfNeedId(expressionOfNeedDTO.getExpressionOfNeedId());
        userEntity.setDescription(expressionOfNeedDTO.getDescription());
        userEntity.setUrgence(expressionOfNeedDTO.getUrgence());
        userEntity.setUserId(expressionOfNeedDTO.getUserId());
        return userEntity;
    }

}
