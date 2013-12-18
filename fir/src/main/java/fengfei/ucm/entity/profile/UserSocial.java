package fengfei.ucm.entity.profile;

/**
 * @User: tietang
 */
public class UserSocial {
    public Integer idUser;
    public String web_site, weibo, qq, qq_weibo, douban, twitter, facebook, flickr, blog, skype, fengniao, renren;

    public static void main(String[] args) {
//        Class<?> clazz = UserSocial.class;
//        String varName = clazz.getSimpleName().substring(0, 1).toLowerCase() + clazz.getSimpleName().substring(1);
//        Field[] fields = clazz.getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            String name = fields[i].getName();
//            String type = fields[i].getType().getSimpleName();
//            System.out.println(type + " " + name + " = rs.getString(\"" + name + "\");");
//        }
//        for (int i = 0; i < fields.length; i++) {
//            String name = fields[i].getName();
//            String type = fields[i].getType().getSimpleName();
//            System.out.println(varName + "." + name + " = " + name + ";");
//            System.out.println(varName + "." + name+",");
//        }
    }

    @Override
    public String toString() {
        return "UserSocial{" +
                "id_user=" + idUser +
                ", web_site='" + web_site + '\'' +
                ", weibo='" + weibo + '\'' +
                ", qq='" + qq + '\'' +
                ", qq_weibo='" + qq_weibo + '\'' +
                ", douban='" + douban + '\'' +
                ", twitter='" + twitter + '\'' +
                ", facebook='" + facebook + '\'' +
                ", flickr='" + flickr + '\'' +
                ", blog='" + blog + '\'' +
                ", skype='" + skype + '\'' +
                ", fengniao='" + fengniao + '\'' +
                ", renren='" + renren + '\'' +
                '}';
    }
}
