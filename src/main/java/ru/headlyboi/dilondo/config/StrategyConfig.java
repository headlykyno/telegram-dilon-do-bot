package ru.headlyboi.dilondo.config;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.headlyboi.dilondo.enums.CommandEnum;
import ru.headlyboi.dilondo.service.api.CommandService;

/**
 * Конфигурация стратегий обработки команд Telegram бота.
 *
 * <p>Этот класс настраивает карту стратегий для обработки различных команд.
 * Каждая команда связана с соответствующим сервисом, который реализует
 * логику обработки конкретного типа команды.</p>
 *
 * <p>Конфигурация использует Spring для автоматического обнаружения
 * всех сервисов команд и создания карты стратегий.</p>
 */
@Configuration
@RequiredArgsConstructor
public class StrategyConfig {

    /**
     * Создает карту стратегий для обработки команд.
     *
     * <p>Преобразует список всех сервисов команд в карту, где ключом
     * является тип команды, а значением - соответствующий сервис.
     * Это позволяет быстро находить нужный сервис для обработки
     * конкретной команды.</p>
     *
     * @param commandServices список всех сервисов команд
     * @return карта стратегий для обработки команд
     */
    @Bean
    public Map<CommandEnum, CommandService> commandServices(List<CommandService> commandServices) {
        return commandServices.stream()
                .collect(Collectors.toMap(CommandService::getCommand, Function.identity()));
    }
}
