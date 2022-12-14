package com.financialportfolio.backend.domain.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.financialportfolio.backend.domain.model.type.AuthorityType;

@Entity
@Table(name = "authorities")
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority", length = 50)
    @Enumerated(EnumType.STRING)
    private AuthorityType authority;

    // Construtores

    public Authority() {
        super();
    }

    public Authority(AuthorityType authority) {
        this();
        this.authority = authority;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    @Override
    public String getAuthority() {
        return this.authority.toString();
    }

    // Métodos

    @Override
    public int hashCode() {
        return Objects.hash(id, authority.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Authority other = (Authority) obj;
        return Objects.equals(id, other.id) && Objects.equals(authority, other.authority);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder
            .append("Authority [id=").append(id).append("]")
            .append("[text=").append(authority.toString()).append("]");
        return builder.toString();
    }

}
