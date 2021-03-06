package cn.sakuratown.jeremyhu.customitems.enchantments;

import cn.sakuratown.jeremyhu.customitems.CustomItems;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentsBuilder {

    private static CustomItems plugin = JavaPlugin.getPlugin(CustomItems.class);

    private List<Enchantment> enchantmentList = new ArrayList<>();

    public EnchantmentsBuilder enchant(Enchantment enchantment){
        enchantmentList.add(enchantment);
        return this;
    }

    public List<Enchantment> build(){
        return enchantmentList;
    }

    public static EnchantmentsBuilder getInstance(){
        return new EnchantmentsBuilder();
    }

    public static EnchantmentsBuilder fromJson(String json){
        EnchantmentsBuilder builder = new EnchantmentsBuilder();
        List<Enchantment> enchantments = new Gson().fromJson(json, new TypeToken<List<Enchantment>>() {}.getType());
        enchantments.forEach(builder::enchant);
        return builder;
    }

    public String getJson(){
        return new Gson().toJson(enchantmentList);
    }

    public static GunEnchantment toGunEnchantment(Enchantment enchantment){
        /*switch (enchantment.getType()){
            case "TrackingEnchantment":
                return new TrackingEnchantment();
            case "MultishotEnchantment":
                return new MultishotEnchantment();
            case "OrbitalEnchantment":
                return new OrbitalEnchantment();
            case "FlamingEnchantment":
                return new FlamingEnchantment();
            case "VampireEnchantment":
                return new VampireEnchantment();
        }

         */
        try{
            //使用反射创建对象
            Class cls = Class.forName(GunEnchantment.class.getPackage().getName() + "." + enchantment.getType());
            return (GunEnchantment)cls.newInstance();
        }catch (Exception e){
            return new GunEnchantment();
        }
    }
}
