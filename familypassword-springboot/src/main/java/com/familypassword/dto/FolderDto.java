package com.familypassword.dto;

import com.familypassword.models.Secret;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class FolderDto extends SecretDto {

    private Set<Secret> children;
}
