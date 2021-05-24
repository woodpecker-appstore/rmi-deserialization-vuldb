package me.gv7.woodpecker.plugin;

import me.gv7.woodpecker.plugin.exploit.RmiDeserialExecExploit;
import me.gv7.woodpecker.plugin.poc.RmiDeserialPoc;

import java.util.ArrayList;
import java.util.List;


public class JavaRMIVulPlugin implements IVulPlugin {
    public static IVulPluginCallbacks callbacks;
    public static IPluginHelper pluginHelper;

    public static String[] gadgets = new String[]{"Jdk7u21",
            "CommonsCollections10","CommonsCollections6",
            "CommonsCollections2","CommonsCollections3","CommonsCollections4","CommonsCollections5","CommonsCollections7","CommonsCollections8","CommonsCollections9","CommonsCollections11",
            "CommonsCollections1",
            "CommonsBeanutils1","CommonsBeanutils1_183",
            "Spring1","Spring2",
            "Hibernate1",
            "ROME",
            "Vaadin1",
            "JSON1",
            "JBossInterceptors1","JavassistWeld1",
            "Vaadin1"
    };

    @Override
    public void VulPluginMain(IVulPluginCallbacks callbacks) {
        this.callbacks = callbacks;
        this.pluginHelper = callbacks.getPluginHelper();
        callbacks.setVulPluginName("java rmi deserialization");
        callbacks.setVulPluginVersion("0.2.0");
        callbacks.setVulPluginAuthor("woodpecker-org");
        callbacks.setVulCVSS(9.5);
        callbacks.setVulName("Java RMI反序列化漏洞");
        callbacks.setVulDescription("该漏洞可以攻击运行RMI服务的端口，进行反序列化执行代码");
        callbacks.setVulCategory("RCE");
        callbacks.setVulProduct("java");
        callbacks.setVulSeverity("high");
        callbacks.registerPoc(new RmiDeserialPoc());

        List<IExploit> exploitList = new ArrayList<IExploit>();
        exploitList.add(new RmiDeserialExecExploit());
        callbacks.registerExploit(exploitList);
    }
}
