package com.qkl.menu;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.qkl.common.collection.ArrayHashMap;
import com.qkl.common.web.PermissionUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MenuUtil {
    private static Logger logger = LoggerFactory.getLogger(MenuUtil.class);

    private static ArrayHashMap<String, Menu> menuMap = ArrayHashMap.newInstance();

    private static Map<String, Set<MenuElement>> permissionElementsMap = Maps.newHashMap();

    private static Set<MenuElement> elementsForAll = Sets.newLinkedHashSet();

    /**
     * 添加菜单
     *
     * @param menu
     */
    public static void addMenu(Menu menu) {
        if (menu == null || menu.getCode() == null) {
            return;
        }
        menuMap.add(menu.getCode(), menu);
    }

    public static Menu getMenu(String code) {
        return menuMap.get(code);
    }

    /**
     * 添加菜单元素
     *
     * @param element
     * @param permissions
     */
    public static void addElement(MenuElement element, String[] permissions) {
        if (permissions == null) {
            elementsForAll.add(element);
        } else {
            for (String p : permissions) {
                permissionElementsMap.computeIfAbsent(p, k -> Sets.newHashSet()).add(element);
            }
        }
    }

    /**
     * 获取用户菜单
     *
     * @param permissionUser
     * @return
     */
    public static List<UserMenu> getUserMenu(PermissionUser permissionUser) {
        ArrayHashMap<String, UserMenu> userMenuMapList = ArrayHashMap.newInstance();

        Set<String> ps = permissionUser.permissions();

        for (String p : ps) {
            Set<MenuElement> mes = permissionElementsMap.get(p);
            if (mes != null) {
                for (MenuElement me : mes) {
                    addElementToUserMenu(userMenuMapList, me);
                }
            }
        }

        for (MenuElement e : elementsForAll) {
            addElementToUserMenu(userMenuMapList, e);
        }

        // 菜单排序
        for (UserMenu um : userMenuMapList.getList()) {
            Collections.sort(um.getMenuElements());
        }

        List<UserMenu> result = userMenuMapList.getList();
        Collections.sort(result);

        return result;
    }

    private static void addElementToUserMenu(ArrayHashMap<String, UserMenu> userMenuMapList, MenuElement menuElement) {
        if (menuElement == null || menuElement.getMenu() == null || StringUtils.isEmpty(menuElement.getMenu().getCode())) {
            return;
        }
        UserMenu userMenu = userMenuMapList.get(menuElement.getMenu().getCode());
        if (userMenu == null) {
            userMenu = new UserMenu(menuElement.getMenu());
            userMenuMapList.add(menuElement.getMenu().getCode(), userMenu);
        }
        userMenu.addElement(menuElement);
    }
}
