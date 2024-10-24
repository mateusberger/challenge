package br.com.mateusberger.challenge.user.domain;

public interface UserPublicInformation {

    Long getId();

    String getUsername();

    RoleEnum getRole();
}
