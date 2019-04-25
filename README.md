# app-performance
## 一、测试范围
    性能指标：
      Android：cpu、内存、流量、帧率、内存溢出、启动时长、anr、crash
      iOS ：cpu、内存、流量、帧率、启动时长
    业务场景（选取用户常用场景）：
    基准线：

## 二、技术选型

    1、腾讯GT

         原理：通过读取adb shell cat /proc/stat的数据计算cpu，通过adb shell dumpsys meminfo来获取内存数据，通过 dumpsys SurfaceFlinger --latency 获取fps，通过读取 /proc/uid_stat/ +uid+ /tcp_snd获取发送跟接收流量，通过/sys/class/power_supply/battery/ueventh计算电量。

         github：https://github.com/Tencent/GT

         优势：开源，自动生成报告，可统计cpu、内存、流量、流畅值、卡顿、页面测速等数据。

         不足：通过启动gt自身app监控其他app，android魅族X6启动gt app卡死，华为mate9（8.0.0）基础性能数据采集不到，ios代码是3年前更新。

    2、Appetizer IO

         原理：上传apk包，自动修改APK中的DEX代码，对已有代码打点监控或者增加新的代码，实现插桩。

         优势：自动生成报告，结合遍历工具自动化，可统计app闪退、anr、主线程卡顿、慢响应等数据。

         不足：没有开源，只适用于android，报告只能导出HAR和cURL格式。
 

    3、DoraemonKit

         github：https://github.com/didi/DoraemonKit

         监控帧率、cpu、内存、流量、卡顿数据。android和ios都支持。

         android暂不支持批量采集，只能单个采集数据。
## 三、各个方面价值
  - 测试：对于每个版本的性能指标数据量化、对比，优化性能、提高产品体验。
  - UI：后期可以加入色值检查、标尺对齐、元素控件铺设状态检查，UI验收简单化，减小对开发的依赖性
  - app本身:增加app开发过程性能检查维度；不用所有问题都引入耗时的开发工具检测，使得开发app多一维度增加严谨性；检查问题过程简单化。
  - 后台开发：待加入（后期考虑集成接口、接口字段、接口状态等检查，减小后台对app端字段、接口检查依赖性）
