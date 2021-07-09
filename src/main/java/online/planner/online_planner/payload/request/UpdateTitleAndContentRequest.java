package online.planner.online_planner.payload.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UpdateTitleAndContentRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
