package greencity.repository;

import greencity.entity.User;
import greencity.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static greencity.enums.EmailNotification.DISABLED;
import static greencity.enums.EmailNotification.IMMEDIATELY;
import static greencity.enums.UserStatus.ACTIVATED;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Sql("classpath:sql/user_repo.sql")
class UserRepoTest {
    @Autowired
    UserRepo userRepo;

    @Test
    void findByEmailTest() {
        Long expected = 1L;
        User actual = userRepo.findByEmail("test@email.com").get();
        assertEquals(expected, actual.getId());
    }

    @Test
    void findAllTest() {
        Pageable pageable = PageRequest.of(0, 3);
        User user1 = userRepo.findByEmail("test@email.com").get();
        User user2 = userRepo.findByEmail("test2@email.com").get();
        User user3 = userRepo.findByEmail("test3@email.com").get();
        List<User> users = Arrays.asList(user1, user2, user3);
        Page<User> actual = new PageImpl<>(users, pageable, users.size());
        Page<User> expected = userRepo.findAll(pageable);
        List<Long> actualIds = actual.getContent().stream().map(User::getId)
            .collect(Collectors.toList());
        List<Long> expectedIds = expected.getContent().stream().map(User::getId)
            .collect(Collectors.toList());
        assertEquals(3, expected.getContent().size());
        assertEquals(expectedIds, actualIds);
    }

    @Test
    void findIdByEmailTest() {
        Long expected = 2L;
        Long actual = userRepo.findIdByEmail("test2@email.com").get();
        assertEquals(expected, actual);
    }

    @Test
    void findUuidByEmail() {
        String expected = "444e66e8-8daa-4cb0-8269-a8d856e7dd15";
        String actual = userRepo.findUuidByEmail("test3@email.com").get();
        assertEquals(expected, actual);
    }

    @Test
    void findNotDeactivatedByEmailTest() {
        User actual = userRepo.findNotDeactivatedByEmail("test@email.com").get();
        assertEquals(1L, actual.getId());
    }

    @Test
    void findAllByEmailNotificationTest() {
        List<User> disabled = userRepo.findAllByEmailNotification(DISABLED);
        List<User> immediately = userRepo.findAllByEmailNotification(IMMEDIATELY);
        assertEquals(7, disabled.size());
        assertEquals(2L, disabled.get(1).getId());
        assertEquals(2, immediately.size());
        assertEquals("test4@email.com", immediately.get(0).getEmail());
    }

    @Test
    void updateUserRefreshTokenTest() {
        String newToken = "NewToken";
        userRepo.updateUserRefreshToken(newToken, 1L);
        User user = userRepo.findByEmail("test@email.com").get();
        assertEquals(newToken, user.getRefreshTokenKey());
    }

    @Test
    void countAllByUserStatusTest() {
        Long expected = 8L;
        Long actual = userRepo.countAllByUserStatus(ACTIVATED);
        assertEquals(expected, actual);
    }

    @Test
    void getProfilePicturePathByUserIdTest() {
        String expected = "pathToPicture";
        String actual = userRepo.getProfilePicturePathByUserId(5L).get();
        assertEquals(expected, actual);
    }


    @Test
    void deactivateSelectedUsersTest() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        userRepo.deactivateSelectedUsers(ids);
        String user1 = userRepo.findByEmail("test@email.com").get().getUserStatus().toString();
        String user2 = userRepo.findByEmail("test2@email.com").get().getUserStatus().toString();
        String user3 = userRepo.findByEmail("test3@email.com").get().getUserStatus().toString();
        String user4 = userRepo.findByEmail("test4@email.com").get().getUserStatus().toString();
        String userStatus = UserStatus.DEACTIVATED.toString();
        String userStatus2 = UserStatus.ACTIVATED.toString();
        assertEquals(user1, userStatus);
        assertEquals(user2, userStatus);
        assertEquals(user3, userStatus);
        assertEquals(user4, userStatus2);
    }

    @Test
    void searchByTest() {
        Pageable pageable = PageRequest.of(0, 3);
        User user3 = userRepo.findByEmail("test3@email.com").get();
        List<User> users = Arrays.asList(user3);
        Page<User> actual = new PageImpl<>(users, pageable, users.size());
        Page<User> expected = userRepo.searchBy(pageable, "test3@email.com");
        List<Long> actualIds = actual.getContent().stream().map(User::getId)
            .collect(Collectors.toList());
        List<Long> expectedIds = expected.getContent().stream().map(User::getId)
            .collect(Collectors.toList());
        assertEquals(1, expected.getContent().size());
        assertEquals(expectedIds, actualIds);
    }

    @Test
    void findAllUsersCitiesTest() {
        List<String> actual = Arrays.asList("New York", "LA", "Chicago", "Miami",
            "Dallas", "Toronto", "Montreal", "Montreal", "Liverpool");
        List<String> expected = userRepo.findAllUsersCities();
        assertEquals(expected, actual);
        assertEquals(9, expected.size());
    }

    @Test
    void countOfMutualFriendsTest() {
        Integer expected = 2;
        Integer friends = userRepo.countOfMutualFriends(1L);
        assertEquals(expected, friends);
    }

    @Test
    void updateUserLastActivityTimeTest() {
        LocalDateTime expected = LocalDateTime.of(2021, 5, 31, 22, 0, 0);
        userRepo.updateUserLastActivityTime(3L, expected);
        User user = userRepo.findByEmail("test3@email.com").get();
        assertEquals(expected, user.getLastActivityTime());
    }

    @Test
    void findLastActivityTimeByIdTest() {
        Timestamp expected = Timestamp.valueOf(LocalDateTime.of(2020, 9, 29, 0, 0, 0));
        Timestamp actual = userRepo.findLastActivityTimeById(8L).get();
        assertEquals(expected, actual);
    }
}
