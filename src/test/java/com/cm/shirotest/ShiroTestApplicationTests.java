package com.cm.shirotest;


import com.cm.shirotest.api.vo.PermissionRoleVo;
import com.cm.shirotest.api.vo.UserRoleVo;
import com.cm.shirotest.entity.User;
import com.cm.shirotest.service.IPermissionRoleService;
import com.cm.shirotest.service.ISimpleCacheService;
import com.cm.shirotest.service.IUserRoleService;
import com.cm.shirotest.service.IUserService;
import com.cm.shirotest.utils.PWDUtil;
import com.cm.shirotest.utils.RedissonSerializable;
import com.cm.shirotest.utils.SpringUtils;
import org.apache.shiro.session.Session;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@SpringBootTest
@ActiveProfiles(value = {"dev","test"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ShiroTestApplicationTests {

    @Autowired
    private IPermissionRoleService permissionRoleService;

    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private IUserService userService;
    @Autowired
    private ISimpleCacheService cacheService;
    @Resource(name = "redissonClientForShiro")
    private RedissonClient redissonClient;

    /**
     * 权限
     */
    @Test
    public void getPermissionRoleVoByRoleId() {

        PermissionRoleVo permissionRoleVo = permissionRoleService.getPermissionRoleVoByRoleId(1L);
    }


    /**
     * 角色
     */

    /**
     * 用户
     */
    @Test
    public void getUserRoleByUserId() {
        UserRoleVo userRoleVo = userRoleService.getUserRoleByUserId(1);
    }

    @Test
    public void test() {
        RBucket<String> bucket = redissonClient.getBucket("group_shiro:global:sessionId:8f0d2613-7e4d-447f-ab18-33e2910d1f9a");
        String sessionStr = bucket.get();
        Session session = (Session) RedissonSerializable.deserialize(sessionStr);
    }

    @Test
    public void testMath() {
        double ceil = Math.ceil(12.6D);
        Calendar instance = Calendar.getInstance();
        LocalDate localDate = LocalDate.now();

        // 反射
        Class<PWDUtil> pwdUtilClass = PWDUtil.class;
        try {
            Method getSalt = pwdUtilClass.getMethod("getSalt", Integer.class);
            Object invoke = getSalt.invoke(pwdUtilClass, 8);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // 集合
        List<User> users = new ArrayList<User>();
        users.removeIf(x -> {
            return x.getUsername().equals("losi");
        });

        TreeSet<User> treeSet = new TreeSet<>((x1, x2) -> x1.getUsername().compareTo(x2.getUsername()));

        Map<String, String> hashMap = new HashMap<>();
        hashMap.merge("key", "value", (key, value) -> {
            return "";
        });

        // 线程池
        BlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<>(20);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10,
                50,
                30,
                TimeUnit.SECONDS,
                blockingQueue,
                Executors.defaultThreadFactory()
        );

        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(50);
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(50);

    }

    @Test
    public void testExecutorService() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        singleThreadExecutor.execute(() -> {
            System.out.println("线程池执行！");
        });
        Future<String> stringFuture = singleThreadExecutor.submit(() -> "你好");
        String str = stringFuture.get(10, TimeUnit.SECONDS);
//        singleThreadExecutor.shutdownNow();
    }

    @Test
    public void testStream() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i <= 3; i++) {
            User user = new User();
            user.setId(i);
            user.setSalt(UUID.randomUUID().toString());
            users.add(user);
        }

        String collect = users.stream()
                .map(User::getSalt)
                .collect(Collectors.joining(",", "prefix_", "_suffix"));

        Map<String, Set<User>> setMap = users.stream()
                .collect(Collectors.groupingBy(User::getPassword, Collectors.toSet()));

        User user = users.stream()
                .max(Comparator.comparing(User::getId))
                .get();

        Map<Integer, Integer> integerMap = users.stream()
                .collect(Collectors.groupingBy(User::getId, Collectors.summingInt(User::getId)));

        users.stream()
                .collect(Collectors.groupingBy(User::getId, Collectors.collectingAndThen(Collectors.toSet(), Set::size)));

        User user1 = new User();
        Optional.ofNullable(user1)
                .ifPresent(user2 -> user1.setUsername("chenmeng"));
    }

}
