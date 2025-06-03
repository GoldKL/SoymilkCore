package com.goldkl.soymilk.command;

import com.goldkl.soymilk.SoymilkCore;
import com.goldkl.soymilk.capability.onspecial.PlayerOnSpecialProvider;
import com.goldkl.soymilk.capability.skillenergy.PlayerSkillEnergyProvider;
import com.goldkl.soymilk.capability.specialenergy.PlayerSpecialEnergyProvider;
import com.goldkl.soymilk.tracking.ForgeEventTracker;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.arguments.BoolArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SoymilkCore.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommandEventHandler {
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("skillenergy")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                .then(Commands.literal("add")
                        .then(Commands.argument("num", DoubleArgumentType.doubleArg()).executes(arguments -> {
                            Player player = EntityArgument.getPlayer(arguments, "player");
                            player.getCapability(PlayerSkillEnergyProvider.PLAYER_SKILL_ENERGY_CAPABILITY).ifPresent(list -> {
                                list.addEnergy(player, DoubleArgumentType.getDouble(arguments, "num"));
                                ForgeEventTracker.syncPlayerSkillEnergy(player);
                                arguments.getSource().sendSuccess(() -> Component.literal(String.format(Component.translatable("translation.command.skillenergy.query").getString(),player.getName().getString(), list.getEnergy()))
                                        ,false);
                            });
                            return 0;
                        })))
                .then(Commands.literal("set")
                        .then(Commands.argument("num", DoubleArgumentType.doubleArg()).executes(arguments -> {
                            Player player = EntityArgument.getPlayer(arguments, "player");
                            player.getCapability(PlayerSkillEnergyProvider.PLAYER_SKILL_ENERGY_CAPABILITY).ifPresent(list -> {
                                list.setEnergy(player, DoubleArgumentType.getDouble(arguments, "num"));
                                ForgeEventTracker.syncPlayerSkillEnergy(player);
                                arguments.getSource().sendSuccess(() -> Component.literal(String.format(Component.translatable("translation.command.skillenergy.query").getString(),player.getName().getString(), list.getEnergy()))
                                        ,false);
                            });
                            return 0;
                        })))
                .then(Commands.literal("query").executes(arguments ->{
                    Player player = EntityArgument.getPlayer(arguments, "player");
                    player.getCapability(PlayerSkillEnergyProvider.PLAYER_SKILL_ENERGY_CAPABILITY).ifPresent(list -> {
                        arguments.getSource().sendSuccess(() -> Component.literal(String.format(Component.translatable("translation.command.skillenergy.query").getString(),player.getName().getString(), list.getEnergy()))
                                ,false);
                    });
                    return 0;
                }))
                )
        );
        dispatcher.register(Commands.literal("specialenergy")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal("add")
                                .then(Commands.argument("num", DoubleArgumentType.doubleArg()).executes(arguments -> {
                                    Player player = EntityArgument.getPlayer(arguments, "player");
                                    player.getCapability(PlayerSpecialEnergyProvider.PLAYER_SPECIAL_ENERGY_CAPABILITY).ifPresent(list -> {
                                        list.addspEnergy(player, DoubleArgumentType.getDouble(arguments, "num"));
                                        ForgeEventTracker.syncPlayerSpecialEnergy(player);
                                        arguments.getSource().sendSuccess(() -> Component.literal(String.format(Component.translatable("translation.command.specialenergy.query").getString(),player.getName().getString(), list.getspEnergy()))
                                                ,false);
                                    });
                                    return 0;
                                })))
                        .then(Commands.literal("set")
                                .then(Commands.argument("num", DoubleArgumentType.doubleArg()).executes(arguments -> {
                                    Player player = EntityArgument.getPlayer(arguments, "player");
                                    player.getCapability(PlayerSpecialEnergyProvider.PLAYER_SPECIAL_ENERGY_CAPABILITY).ifPresent(list -> {
                                        list.setspEnergy(player, DoubleArgumentType.getDouble(arguments, "num"));
                                        ForgeEventTracker.syncPlayerSpecialEnergy(player);
                                        arguments.getSource().sendSuccess(() -> Component.literal(String.format(Component.translatable("translation.command.specialenergy.query").getString(),player.getName().getString(), list.getspEnergy()))
                                                ,false);
                                    });
                                    return 0;
                                })))
                        .then(Commands.literal("query").executes(arguments ->{
                            Player player = EntityArgument.getPlayer(arguments, "player");
                            player.getCapability(PlayerSpecialEnergyProvider.PLAYER_SPECIAL_ENERGY_CAPABILITY).ifPresent(list -> {
                                arguments.getSource().sendSuccess(() -> Component.literal(String.format(Component.translatable("translation.command.specialenergy.query").getString(),player.getName().getString(), list.getspEnergy()))
                                        ,false);
                            });
                            return 0;
                        }))
                )
        );
        dispatcher.register(Commands.literal("onspecial")
                .requires(source -> source.hasPermission(2))
                .then(Commands.argument("player", EntityArgument.player())
                        .then(Commands.literal("set")
                                .then(Commands.argument("bool", BoolArgumentType.bool()).executes(arguments -> {
                                    Player player = EntityArgument.getPlayer(arguments, "player");
                                    player.getCapability(PlayerOnSpecialProvider.PLAYER_ON_SPECIAL_CAPABILITY).ifPresent(list -> {
                                        list.setOnSpecial( BoolArgumentType.getBool(arguments, "bool"));
                                        ForgeEventTracker.syncPlayerOnSpecial(player);
                                        arguments.getSource().sendSuccess(() -> Component.literal(String.format(Component.translatable("translation.command.onspecial.query").getString(),player.getName().getString(), list.getOnSpecial()))
                                                ,false);
                                    });
                                    return 0;
                                })))
                        .then(Commands.literal("query").executes(arguments ->{
                            Player player = EntityArgument.getPlayer(arguments, "player");
                            player.getCapability(PlayerOnSpecialProvider.PLAYER_ON_SPECIAL_CAPABILITY).ifPresent(list -> {
                                arguments.getSource().sendSuccess(() -> Component.literal(String.format(Component.translatable("translation.command.onspecial.query").getString(),player.getName().getString(), list.getOnSpecial()))
                                        ,false);
                            });
                            return 0;
                        }))
                )
        );
        //MyCustomCommand.register(event.getDispatcher());
    }
}