package com.b2camp.simple_core_banking.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "m_user", schema = "public")
public class MUser extends BaseReference {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "user_id", nullable = false, length = 70)
    private String userId;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "email", length = 70)
    private String email;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "password", length = 255)
    private String password;

    @Column(name = "fullname", length = 100)
    private String fullname;

    @Column(name = "token", length = 225)
    private String token;

    @Column(name = "session_id", length = 225)
    private String sessionId;

    @ManyToOne
    @JoinColumn(name = "user_role_id", referencedColumnName = "user_role_id")
    private MUserRole mUserRole;
}
