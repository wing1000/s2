package fengfei.spruce.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimpleCache {
    public static Map<Byte, String> categories = new LinkedHashMap<>();
    public static Map<String, String> licenses = new LinkedHashMap<>();
    /**
     * <pre>
     * 常用的标签：
     *  |- 风光 花卉 植物 自然
     * 题材：
     *  |- 人像 人文 风光 纪实 静物 旅行 生态 生活 城市 街拍 花卉 猫 夜景 建筑 动物 植物
     *  儿童 光影 美女 婚礼 古镇 狗 社会 汽车 自然 校园 云 夕阳 春天 海 美食 女孩 舞台
     *  夏天 摇滚 荷花 自拍 阳光 秋天 铁路 民俗 单车 私房 花草 草原 宠物 日出 树 樱花 婚纱
     *  女生 地铁 雪景 公园 海鸥 老人 蓝天 教堂 黄昏 模特 车展 鸟 冬天 晚霞 百姓 昆虫 植物园
     *  剪影 毕业 大学 雪山 运动 水 乡村 倒影
     *  |- 风景，建筑
     * 地方：
     *  |- 新疆，西藏
     * 器材:
     * 风格:
     *  |- 黑白 色彩 小清新 lomo 微距 极简主义 情感 小品 抓拍 后期 长曝光 HDR 故事 日系 心情 逆光 写真 情绪 概念 绿色 艺术 复古 可爱 时尚 创意 蓝色 安静 文化 跟拍 观念 现场 幻想 lightroom 性感 印象 RAW
     * </pre>
     */
    public static Map<String, List<String>> Tags = new LinkedHashMap<>();


}
