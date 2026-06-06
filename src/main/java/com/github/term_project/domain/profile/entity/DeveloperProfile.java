package com.github.term_project.domain.profile.entity;

import com.github.term_project.domain.user.entity.User;
import com.github.term_project.global.common.BaseTimeEntity;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "developer_profiles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeveloperProfile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(length = 255)
    private String profileImagePath;

    @Column(nullable = false)
    private boolean activeAvailable;

    @Column(nullable = false)
    private boolean onsiteAvailable;

    @Column(nullable = false, length = 50)
    private String regionSido;

    @Column(nullable = false, length = 50)
    private String regionSigungu;

    @Column(nullable = false, length = 50)
    private String businessType;

    @Column(nullable = false)
    private int careerYears;

    @Column(nullable = false, length = 5000)
    private String introduction;

    @ElementCollection
    @CollectionTable(name = "developer_profile_support_fields", joinColumns = @JoinColumn(name = "profile_id"))
    @OrderColumn(name = "field_order")
    @Column(name = "field_name", nullable = false, length = 30)
    private List<String> supportFields = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "developer_profile_search_tags", joinColumns = @JoinColumn(name = "profile_id"))
    @OrderColumn(name = "tag_order")
    @Column(name = "tag_name", nullable = false, length = 30)
    private List<String> searchTags = new ArrayList<>();

    @Builder
    public DeveloperProfile(
            User user,
            String profileImagePath,
            boolean activeAvailable,
            boolean onsiteAvailable,
            String regionSido,
            String regionSigungu,
            String businessType,
            int careerYears,
            String introduction,
            List<String> supportFields,
            List<String> searchTags) {
        this.user = user;
        this.profileImagePath = profileImagePath;
        this.activeAvailable = activeAvailable;
        this.onsiteAvailable = onsiteAvailable;
        this.regionSido = regionSido;
        this.regionSigungu = regionSigungu;
        this.businessType = businessType;
        this.careerYears = careerYears;
        this.introduction = introduction;
        this.supportFields = supportFields == null ? new ArrayList<>() : new ArrayList<>(supportFields);
        this.searchTags = searchTags == null ? new ArrayList<>() : new ArrayList<>(searchTags);
    }

    public static DeveloperProfile createDefault(User user) {
        return DeveloperProfile.builder()
                .user(user)
                .profileImagePath(null)
                .activeAvailable(false)
                .onsiteAvailable(false)
                .regionSido("")
                .regionSigungu("")
                .businessType("")
                .careerYears(0)
                .introduction("")
                .supportFields(List.of())
                .searchTags(List.of())
                .build();
    }

    public void updateProfile(
            List<String> supportFields,
            boolean activeAvailable,
            boolean onsiteAvailable,
            String regionSido,
            String regionSigungu,
            String businessType,
            int careerYears,
            List<String> searchTags,
            String introduction) {
        this.supportFields.clear();
        this.supportFields.addAll(supportFields);
        this.activeAvailable = activeAvailable;
        this.onsiteAvailable = onsiteAvailable;
        this.regionSido = regionSido;
        this.regionSigungu = regionSigungu;
        this.businessType = businessType;
        this.careerYears = careerYears;
        this.searchTags.clear();
        this.searchTags.addAll(searchTags);
        this.introduction = introduction;
    }

    public void updateProfileImagePath(String profileImagePath) {
        this.profileImagePath = profileImagePath;
    }
}
