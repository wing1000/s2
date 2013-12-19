package fengfei.web.authority;

import static fengfei.web.authority.AuthorityType.Create;
import static fengfei.web.authority.AuthorityType.Delete;
import static fengfei.web.authority.AuthorityType.Modify;
import static fengfei.web.authority.AuthorityType.Read;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authority {

    Role role() default Role.Guest;

    AuthorityType[] authorityTypes() default { Create, Read, Modify, Delete };

    ResponseType responseType() default ResponseType.HtmlPage;
}
