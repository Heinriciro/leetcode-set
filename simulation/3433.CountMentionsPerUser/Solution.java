import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Solution {
    // [法一]：排序 + 模拟
    public int[] countMentions(int numberOfUsers, List<List<String>> events) {
        // 排序
        events.sort((List<String> o1, List<String> o2) -> {
            int timeDiff = Integer.parseInt(o1.get(1)) - Integer.parseInt(o2.get(1));
            if (timeDiff != 0 || ("OFFLINE".equals(o1.get(0)) && "OFFLINE".equals(o2.get(0)))) return timeDiff;
            if ("OFFLINE".equals(o1.get(0))) return -1;
            if ("OFFLINE".equals(o1.get(1))) return 1;
            return 0;
        });

        int[] latestOnlineTime = new int[numberOfUsers];
        Arrays.fill(latestOnlineTime, -1);
        int[] res = new int[numberOfUsers];
        for (List<String> event : events) {
            String eventType = event.get(0);
            int timestamp = Integer.parseInt(event.get(1));
            if ("OFFLINE".equals(eventType)) {
                latestOnlineTime[Integer.parseInt(event.get(2))] = timestamp - 1;
            } else if ("MESSAGE".equals(eventType)) {
                switch (event.get(2)) {
                    case "ALL" -> {
                        for (int i = 0; i < numberOfUsers; i++) {
                            res[i] += 1;
                        }
                    }
                    case "HERE" -> {
                        for (int i = 0; i < numberOfUsers; i++) {
                            if (latestOnlineTime[i] == -1|| latestOnlineTime[i] + 60 < timestamp) res[i] += 1;
                        }
                    }
                    default -> {
                        String[] ids = event.get(2).split(" ");
                        for (String id : ids) {
                            int idx = Integer.parseInt(id.substring(2));
                            res[idx] += 1;
                        }
                    }
                }
            }
        }

        return res;
    }

    public static void main(String[] args) {
        List<List<List<String>>> testEvents = Stream.of(
            Arrays.asList(Arrays.asList("MESSAGE","10","id1 id0"),Arrays.asList("OFFLINE","11","0"),Arrays.asList("MESSAGE","71","HERE")),
            Arrays.asList(Arrays.asList("MESSAGE","10","id1 id0"),Arrays.asList("OFFLINE","11","0"),Arrays.asList("MESSAGE","12","ALL")),
            Arrays.asList(Arrays.asList("OFFLINE","10","0"), Arrays.asList("MESSAGE","12","HERE")),
            Arrays.asList(Arrays.asList("MESSAGE","2","HERE"), Arrays.asList("OFFLINE","2","1"), Arrays.asList("OFFLINE","1","0"),Arrays.asList("MESSAGE","61","HERE"))
        ).collect(Collectors.toList());
        int[] testUserNums = {2, 2, 2, 3};
        
        Solution s = new Solution();

        for (int i = 0; i < testEvents.size(); i++) {
            System.out.println("No."+i+" test case: " + Arrays.toString(s.countMentions(testUserNums[i], testEvents.get(i))));
        }
    }
}