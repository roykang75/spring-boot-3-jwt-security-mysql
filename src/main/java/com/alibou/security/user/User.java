package com.alibou.security.user;

import com.alibou.security.common.entity.CommonDateEntity;
import com.alibou.security.role.Role;
import com.alibou.security.token.Token;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Builder
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class User extends CommonDateEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userSeq;

  @Column(nullable = false, length = 10)
  private String firstname;

  @Column(nullable = false, length = 30)
  private String lastname;

  @Column(nullable = false, length = 100)
  private String email;

  @Column(nullable = false, length = 100)
  private String password;

  @Embedded
  private Address address;

//  @JsonIgnore
//  @OneToOne(mappedBy = "user")
//  private Role role;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_seq")
  private Role role;

  @OneToMany(mappedBy = "user")
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return role.getAuthorities();
  }

  public User(String firstname, String lastname, String email, String password, Address address) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.email = email;
    this.password = password;

      this.address = Address.builder()
            .zipCode(address.getZipCode())
            .address1(address.getAddress1())
            .address2(address.getAddress2())
            .build();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
