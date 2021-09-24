package online.planner.online_planner.controller;

import lombok.RequiredArgsConstructor;
import online.planner.online_planner.util.FcmUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PushMessageTestController {

    private final FcmUtil fcmUtil;

    @GetMapping("/test/push/{deviceToken}")
    public void testPush(@PathVariable String deviceToken) {
        List<String> tokens = new ArrayList<>();
        tokens.add(deviceToken);
        fcmUtil.sendPushMessage(tokens, "테스트", "서버에서 보내는 테스트 메세지");
    }
}
