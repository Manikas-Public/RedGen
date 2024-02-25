package com.manikas.redgen.entity.aigenerator;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GPTQueryLauncher {

    private static Logger LOGGER = LogUtils.getLogger();

    public static String queryAI (String prompt) {

        String output = null;
        String finalOutput = null;

        StringBuilder outputBuilder = new StringBuilder();

        try {

            // makes cmd command to run
            Process pythonQueryer = Runtime.getRuntime().exec("python queryGPT.py" + " " + prompt);

            // creates readers for output and errors
            BufferedReader response = new BufferedReader(new InputStreamReader(pythonQueryer.getInputStream()));
            BufferedReader errors = new BufferedReader(new InputStreamReader(pythonQueryer.getErrorStream()));

            // gets output
            while ((output = response.readLine()) != null) {
                LOGGER.info("Python : " + output);
                outputBuilder.append(output);
            }

            while ((output = errors.readLine()) != null) {
                LOGGER.error("Error : " + output);
                Minecraft.getInstance().player.sendSystemMessage(Component.translatable("cmd.redgen.general_error").append(Component.literal(output)));
            }

            finalOutput = outputBuilder.toString();

            return finalOutput;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
