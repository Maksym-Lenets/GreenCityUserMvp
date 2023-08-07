package greencity;

import greencity.constant.AppConstant;
import greencity.dto.UbsCustomerDto;
import greencity.dto.econews.AddEcoNewsDtoResponse;
import greencity.dto.language.LanguageVO;
import greencity.dto.ownsecurity.OwnSecurityVO;
import greencity.dto.position.PositionAuthoritiesDto;
import greencity.dto.position.PositionDto;
import greencity.dto.ubs.UbsProfileCreationDto;
import greencity.dto.user.EcoNewsAuthorDto;
import greencity.dto.user.UserAdminRegistrationDto;
import greencity.dto.user.UserAllFriendsDto;
import greencity.dto.user.UserEmployeeAuthorityDto;
import greencity.dto.user.UserManagementDto;
import greencity.dto.user.UserManagementUpdateDto;
import greencity.dto.user.UserProfileDtoRequest;
import greencity.dto.user.UserProfilePictureDto;
import greencity.dto.user.UserProfileStatisticsDto;
import greencity.dto.user.UserVO;
import greencity.dto.user.UsersFriendDto;
import greencity.dto.verifyemail.VerifyEmailVO;
import greencity.dto.violation.UserViolationMailDto;

import greencity.entity.Language;
import greencity.entity.OwnSecurity;
import greencity.entity.RestorePasswordEmail;
import greencity.entity.SocialNetwork;
import greencity.entity.User;
import greencity.entity.VerifyEmail;
import greencity.enums.EmailNotification;
import greencity.enums.Role;
import greencity.enums.UserStatus;
import greencity.security.dto.ownsecurity.EmployeeSignUpDto;
import greencity.security.dto.ownsecurity.OwnRestoreDto;
import greencity.security.dto.ownsecurity.OwnSignUpDto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelUtils {
    public static final User TEST_USER = createUser();
    public static final User TEST_ADMIN = createAdmin();
    public static final UserVO TEST_USER_VO = createTestUserVO();
    public static final OwnSecurity TEST_OWN_SECURITY = createOwnSecurity();
    public static final RestorePasswordEmail TEST_RESTORE_PASSWORD_EMAIL = createRestorePasswordEmail();
    public static final RestorePasswordEmail TEST_RESTORE_PASSWORD_EMAIL_EXPIRED_TOKEN =
        createRestorePasswordEmailExpiredToken();
    public static final OwnRestoreDto TEST_OWN_RESTORE_DTO = createOwnRestoreDto();
    public static final OwnRestoreDto TEST_OWN_RESTORE_DTO_WRONG = createOwnRestoreDtoWrong();
    public static final UserProfileStatisticsDto USER_PROFILE_STATISTICS_DTO = createUserProfileStatisticsDto();
    public static final UserManagementDto CREATE_USER_MANAGER_DTO = createUserManagerDto();
    public static final List<UserAllFriendsDto> CREATE_USER_ALL_FRIENDS_DTO = createUserAllFriendsDto();
    public static final String TEST_EMAIL = "taras@gmail.com";

    public static UsersFriendDto usersFriendDto = new UsersFriendDto() {
        @Override
        public Long getId() {
            return 1L;
        }

        @Override
        public String getName() {
            return TestConst.NAME;
        }

        @Override
        public String getCity() {
            return "test";
        }

        @Override
        public Double getRating() {
            return 20.0;
        }

        @Override
        public String getProfilePicture() {
            return "profile";
        }
    };

    private static UserManagementDto createUserManagerDto() {
        return UserManagementDto.builder()
            .id(1L)
            .name("Martin")
            .email("martin@gmail.com")
            .userCredo("credo")
            .role(Role.ROLE_ADMIN)
            .userStatus(UserStatus.ACTIVATED).build();
    }

    public static UserManagementUpdateDto getUserManagementUpdateDto() {
        return UserManagementUpdateDto.builder()
            .name(TestConst.NAME)
            .role(Role.ROLE_USER)
            .email(TestConst.EMAIL)
            .userCredo(TestConst.CREDO)
            .build();
    }

    private static UserProfileStatisticsDto createUserProfileStatisticsDto() {
        return UserProfileStatisticsDto.builder()
            .amountHabitsInProgress(TestConst.SIMPLE_LONG_NUMBER)
            .amountHabitsAcquired(TestConst.SIMPLE_LONG_NUMBER)
            .amountPublishedNews(TestConst.SIMPLE_LONG_NUMBER)
            .build();
    }

    private static List<UserAllFriendsDto> createUserAllFriendsDto() {
        List<UserAllFriendsDto> list = new ArrayList<>();

        list.add(UserAllFriendsDto.builder()
            .id(1L)
            .name("Martin")
            .city("New-York")
            .rating(30.00)
            .mutualFriends(11L)
            .profilePicturePath("Picture")
            .build());
        list.add(UserAllFriendsDto.builder()
            .id(1L)
            .name("Martin")
            .city("New-York")
            .rating(30.00)
            .mutualFriends(11L)
            .profilePicturePath("Picture")
            .build());
        list.add(UserAllFriendsDto.builder()
            .id(1L)
            .name("Martin")
            .city("New-York")
            .rating(30.00)
            .mutualFriends(11L)
            .profilePicturePath("Picture")
            .build());
        return list;
    }

    public static UserEmployeeAuthorityDto getUserEmployeeAuthorityDto() {
        return UserEmployeeAuthorityDto.builder()
            .employeeEmail("taras@gmail.com")
            .authorities(List.of("test1"))
            .build();
    }

    public static UserEmployeeAuthorityDto getSuperAdminEmployeeAuthorityDto() {
        return UserEmployeeAuthorityDto.builder()
            .employeeEmail("superadmin@gmail.com")
            .authorities(List.of("test1"))
            .build();
    }

    public static User getUser() {
        return User.builder()
            .id(1L)
            .email(TestConst.EMAIL)
            .name(TestConst.NAME)
            .role(Role.ROLE_USER)
            .lastActivityTime(LocalDateTime.now())
            .verifyEmail(new VerifyEmail())
            .dateOfRegistration(LocalDateTime.now())
            .build();
    }

    public static User getUserWithSocialNetworks() {
        List<SocialNetwork> socialNetwork = new ArrayList<>();
        socialNetwork.add(SocialNetwork.builder()
            .id(1L)
            .url("https://www.facebook.com")
            .build());
        return User.builder()
            .id(1L)
            .email(TestConst.EMAIL)
            .name(TestConst.NAME)
            .role(Role.ROLE_USER)
            .language(ModelUtils.getLanguage())
            .socialNetworks(socialNetwork)
            .lastActivityTime(LocalDateTime.now())
            .verifyEmail(new VerifyEmail())
            .dateOfRegistration(LocalDateTime.now())
            .build();
    }

    public static User getUserWithUbsRole() {
        return User.builder()
            .id(1L)
            .email(TestConst.EMAIL)
            .name(TestConst.NAME)
            .role(Role.ROLE_UBS_EMPLOYEE)
            .lastActivityTime(LocalDateTime.now())
            .verifyEmail(new VerifyEmail())
            .restorePasswordEmail(new RestorePasswordEmail())
            .dateOfRegistration(LocalDateTime.now())
            .build();
    }

    public static UserVO getUserVO() {
        return UserVO.builder()
            .id(1L)
            .email(TestConst.EMAIL)
            .name(TestConst.NAME)
            .role(Role.ROLE_USER)
            .lastActivityTime(LocalDateTime.now())
            .verifyEmail(new VerifyEmailVO())
            .dateOfRegistration(LocalDateTime.now())
            .build();
    }

    public static UbsCustomerDto getUbsCustomerDtoWithData() {
        return UbsCustomerDto.builder()
            .email("nazar.struk@gmail.com")
            .build();
    }

    public static UserVO getUserVOWithData() {
        return UserVO.builder()
            .id(13L)
            .name("user")
            .email("namesurname1995@gmail.com")
            .role(Role.ROLE_USER)
            .userCredo("save the world")
            .emailNotification(EmailNotification.MONTHLY)
            .userStatus(UserStatus.ACTIVATED)
            .rating(13.4)
            .verifyEmail(VerifyEmailVO.builder()
                .id(32L)
                .user(UserVO.builder()
                    .id(13L)
                    .name("user")
                    .build())
                .expiryDate(LocalDateTime.of(2021, 7, 7, 7, 7))
                .token("toooookkkeeeeen42324532542")
                .build())
            .userFriends(Collections.singletonList(
                UserVO.builder()
                    .id(75L)
                    .name("Andrew")
                    .build()))
            .refreshTokenKey("refreshtoooookkkeeeeen42324532542")
            .ownSecurity(null)
            .dateOfRegistration(LocalDateTime.of(2020, 6, 6, 13, 47))
            .city("Lviv")
            .showShoppingList(true)
            .showEcoPlace(true)
            .showLocation(true)
            .ownSecurity(OwnSecurityVO.builder()
                .id(1L)
                .password("password")
                .user(UserVO.builder()
                    .id(13L)
                    .build())
                .build())
            .lastActivityTime(LocalDateTime.of(2020, 12, 11, 13, 30))
            .languageVO(LanguageVO.builder()
                .id(1L)
                .code("ua")
                .build())
            .build();
    }

    public static UserProfileDtoRequest getUserProfileDtoRequest() {
        return UserProfileDtoRequest.builder()
            .name("Name")
            .city("City")
            .userCredo("userCredo")
            .socialNetworks(List.of(
                "https://www.facebook.com",
                "https://www.instagram.com",
                "https://www.youtube.com",
                "https://www.gmail.com",
                "https://www.google.com"))
            .showLocation(true)
            .showEcoPlace(true)
            .showShoppingList(true)
            .build();
    }

    public static Language getLanguage() {
        return Language.builder().id(1L).code(AppConstant.DEFAULT_LANGUAGE_CODE).build();
    }

    public static UserProfilePictureDto getUserProfilePictureDto() {
        return new UserProfilePictureDto(1L, "name", "image");
    }

    public static EcoNewsAuthorDto getEcoNewsAuthorDto() {
        return EcoNewsAuthorDto.builder()
            .id(1L)
            .name("taras")
            .build();
    }

    public static AddEcoNewsDtoResponse getAddEcoNewsDtoResponse() {
        return AddEcoNewsDtoResponse.builder()
            .id(1L)
            .title("title")
            .text("texttexttexttext")
            .ecoNewsAuthorDto(getEcoNewsAuthorDto())
            .creationDate(ZonedDateTime.now())
            .imagePath("/imagePath")
            .source("source")
            .tags(Collections.singletonList("tag"))
            .build();
    }

    public static UserViolationMailDto getUserViolationMailDto() {
        return UserViolationMailDto.builder()
            .email("string@gmail.com")
            .name("string")
            .language("en")
            .violationDescription("String Description")
            .build();
    }

    public static UserAdminRegistrationDto getUserAdminRegistrationDto() {
        return UserAdminRegistrationDto.builder()
            .id(1L)
            .email(TestConst.EMAIL)
            .name(TestConst.NAME)
            .role(Role.ROLE_USER)
            .userStatus(UserStatus.BLOCKED)
            .languageCode("en")
            .dateOfRegistration(LocalDateTime.of(2020, 6, 6, 13, 47))
            .build();
    }

    private static RestorePasswordEmail createRestorePasswordEmailExpiredToken() {
        return RestorePasswordEmail.builder()
            .token("test")
            .id(1L)
            .user(TEST_USER)
            .expiryDate(LocalDateTime.now().minusDays(1L))
            .build();
    }

    private static OwnRestoreDto createOwnRestoreDtoWrong() {
        return OwnRestoreDto.builder()
            .token("test")
            .password("TestPassword&1")
            .confirmPassword("TestPassword&2")
            .build();
    }

    private static OwnSecurity createOwnSecurity() {
        return OwnSecurity.builder()
            .user(TEST_USER)
            .build();
    }

    private static User createUser() {
        return User.builder()
            .id(2L)
            .email("test@mail.com")
            .userStatus(UserStatus.CREATED)
            .role(Role.ROLE_USER)
            .build();
    }

    public static User createAdmin() {
        return User.builder()
            .id(2L)
            .email("test@mail.com")
            .userStatus(UserStatus.CREATED)
            .role(Role.ROLE_ADMIN)
            .build();
    }

    private static OwnRestoreDto createOwnRestoreDto() {
        return OwnRestoreDto.builder()
            .token("test")
            .password("TestPassword&1")
            .confirmPassword("TestPassword&1")
            .build();
    }

    private static RestorePasswordEmail createRestorePasswordEmail() {
        return RestorePasswordEmail.builder()
            .token("test")
            .id(1L)
            .user(TEST_USER)
            .expiryDate(LocalDateTime.now().plusDays(1L))
            .build();
    }

    private static UserVO createTestUserVO() {
        return UserVO.builder()
            .id(2L)
            .email("test@mail.com")
            .userStatus(UserStatus.CREATED)
            .role(Role.ROLE_ADMIN)
            .build();
    }

    public static EmployeeSignUpDto getEmployeeSignUpDto() {
        return EmployeeSignUpDto.builder()
            .name("Taras")
            .email("test@mail.com")
            .positions(List.of(PositionDto.builder()
                .id(1L)
                .name("test")
                .build()))
            .isUbs(true)
            .build();
    }

    public static OwnSignUpDto getOwnSignUpDto() {
        return OwnSignUpDto.builder()
            .name("Name")
            .email("test@mail.com")
            .password("Test@123")
            .isUbs(true)
            .build();
    }

    public static UbsProfileCreationDto getUbsProfileCreationDto() {
        return UbsProfileCreationDto.builder()
            .name("UbsProfile")
            .email("ubsuser@mail.com")
            .uuid("f81d4fae-7dec-11d0-a765-00a0c91e6bf6")
            .build();
    }

    public static PositionAuthoritiesDto getPositionAuthoritiesDto() {
        return PositionAuthoritiesDto.builder()
            .positionId(List.of(1L))
            .authorities(List.of("Auth"))
            .build();
    }
}
