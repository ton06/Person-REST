package br.com.wellington.o.a.calculadorarest.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails, Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name", unique = true)
	private String userName;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "password")
	private String password;

	@Column(name = "account_non_expired")
	private Boolean accountNotExpired;

	@Column(name = "account_non_locked")
	private Boolean accountNotLocked;

	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

	@Column(name = "enabled")
	private Boolean enable;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_permission", joinColumns = {@JoinColumn(name = "id_user")},
			inverseJoinColumns = {@JoinColumn(name = "id_permission")})
	private List<Permission> permissionList;

	public List<String> getRoles() {
		List<String> roles = new ArrayList<>();
		permissionList.forEach(permission -> {
			roles.add(permission.getDescription());
		});
		return roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getPermissionList();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNotExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNotLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enable;
	}
}
