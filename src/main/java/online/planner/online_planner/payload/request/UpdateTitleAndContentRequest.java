package online.planner.online_planner.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateTitleAndContentRequest {
    private String title;
    private String content;
}
