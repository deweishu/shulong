package com.qkl.menu;

import com.qkl.common.permission.RequestPermission;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 菜单搜索
 */
@Component
public class MenuScanner {
    @PostConstruct
    public void init() throws ClassNotFoundException, IllegalAccessException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(MenuType.class));
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents("com.qkl.**")) {
            Class<?> c = Class.forName(beanDefinition.getBeanClassName());

            Field[] fields = c.getDeclaredFields();
            for (Field field : fields) {
                if (!Modifier.isStatic(field.getModifiers()) || !field.getType().isAssignableFrom(String.class)) {
                    continue;
                }

                MenuDefiner md = field.getAnnotation(MenuDefiner.class);
                if (md != null) {
                    MenuUtil.addMenu(new Menu((String) field.get(null), md.name(), md.icon(), md.priority()));
                }
            }
        }

        scanner.resetFilters(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));
        for (BeanDefinition beanDefinition : scanner.findCandidateComponents("com.qkl.**.web")) {
            Class<?> c = Class.forName(beanDefinition.getBeanClassName());

            String prefix = "";

            RequestMapping crm = c.getAnnotation(RequestMapping.class);
            if (crm != null && crm.value().length > 0)
                prefix = crm.value()[0];

            Method[] ms = c.getMethods();
            for (Method m : ms) {
                GetMapping gm = m.getAnnotation(GetMapping.class);
                RequestMapping rm = m.getAnnotation(RequestMapping.class);
                if (rm != null || gm != null) {
                    String[] mappings = gm != null ? gm.value() : rm.value();
                    MenuMapping md = m.getAnnotation(MenuMapping.class);
                    if (md != null) {
                        RequestPermission rp = m.getAnnotation(RequestPermission.class);

                        MenuElement me = new MenuElement(md.value(), url(prefix, mappings), MenuUtil.getMenu(md.menu()), md.weight());
                        if (rp == null) {
                            MenuUtil.addElement(me, null);
                        } else if ((rp.value().length == 1 && "".equals(rp.value()[0]))) {
                            MenuUtil.addElement(me, null);
                        } else {
                            MenuUtil.addElement(me, rp.value());
                        }
                    }
                }
            }
        }
    }

    private String url(String prefix, String[] mappings) {
        String url = "";
        if (mappings.length > 0)
            url = mappings[0];
        String s = ("/" + prefix + "/" + url).replace("//", "/");
        if (s.endsWith("/"))
            s = s.substring(0, s.length() - 1);
        return s;
    }
}
