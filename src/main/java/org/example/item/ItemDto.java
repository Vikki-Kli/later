package org.example.item;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {

    @NotBlank
    private String username;
    @NotBlank
    private String url;
    private String description;
}
