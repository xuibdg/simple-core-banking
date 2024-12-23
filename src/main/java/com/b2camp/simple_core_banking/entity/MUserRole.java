package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "m_user_role", schema = "public")
public class MUserRole {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "user_role_id", nullable = false)
    private String userRoleId;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "is_deleted")
    private boolean isDeleted;
}
