package com.example.smarthousema;

import java.util.BitSet;

public class AddressSpace {
    private String commandNumber = "";
    public String getURLcommand(int commandNumber){
        switch (commandNumber) {
//            case 0:
//                this.commandNumber = "00000000";                                                                      // Основное освещение;
//                break;
            case 128:
                this.commandNumber = "01000000";                                                                        // Отключить все;
                break;
            case 161:
                this.commandNumber = "10100001";                                                                        // Случайный режим интерьерного освещение;
                break;
            case 162:
                this.commandNumber = "10100010";                                                                        // Постоянный свет;
                break;
            case 163:
                this.commandNumber = "10100011";                                                                        // Плавная смена цвета;
                break;
            case 164:
                this.commandNumber = "10100100";                                                                        // Бегущая радуга;
                break;
            case 224:
                this.commandNumber = "11100000";                                                                        // Случайный режим динамического освещения;
                break;
            case 225:
                this.commandNumber = "11100001";                                                                        // Шкала громкости (градиент);
                break;
            case 226:
                this.commandNumber = "11100010";                                                                        // Шкала громкости (радуга);
                break;
            case 227:
                this.commandNumber = "11100011";                                                                        // Цветомузыка (5 полос);
                break;
            case 228:
                this.commandNumber = "11100100";                                                                        // Цветомузыка (3 полосы);
                break;
            case 229:
                this.commandNumber = "11100101";                                                                        // Цветомузыка (1 полоса - 3 частоты);
                break;
            case 230:
                this.commandNumber = "11100110";                                                                        // Цветомузыка (1 полоса - низкие);
                break;
            case 231:
                this.commandNumber = "11100111";                                                                        // Цветомузыка (1 полоса - средние);
                break;
            case 232:
                this.commandNumber = "11101000";                                                                        // Цветомузыка (1 полоса - высокие)
                break;
            case 233:
                this.commandNumber = "11101001";                                                                        // Стробоскоп
                break;
            case 234:
                this.commandNumber = "11101010";                                                                        // Бегущие частоты (3 частоты)
                break;
            case 235:
                this.commandNumber = "11101011";                                                                        // Бегущие частоты (низкие)
                break;
            case 236:
                this.commandNumber = "11101100";                                                                        // Бегущие частоты (средние)
                break;
            case 237:
                this.commandNumber = "11101101";                                                                        // Бегущие частоты (высокие)
                break;
            case 238:
                this.commandNumber = "11101101";                                                                        // Анализатор спектра
                break;

            default:
                this.commandNumber = "00000000";                                                                        // Основное освещение;
                break;
        }
        return this.commandNumber;
    }

    public String getURLcommand(int commandNumber, int value){
        String firstPart = "";
        String[] secondPart = {"0", "0", "0", "0", "0"};
        switch (commandNumber) {
            case 32:
                firstPart = "001";                                                                                      // Режим изменения цвета;
                break;
            case 96:
                firstPart = "011";                                                                                      // Режим изменения скорости;
                break;
            default:
                break;
        }
        if (value < 32) {
            BitSet bitSet = new BitSet();
            Boolean[] valueBoolean = {false, false, false, false, false};

            for (int i = 4; i >= 0; i--) {
                if (((1 << i) & value) != 0) {
                    bitSet.set(i);
                } else {
                    bitSet.clear(i);
                }
                valueBoolean[i] = bitSet.get(i);
            }

            for (int i = 0; i < 5; i++) {
                if (valueBoolean[i]) {
                    secondPart[i] = "1";
                }
            }

        }

        this.commandNumber = firstPart + secondPart[4] + secondPart[3] + secondPart[2] + secondPart[1] + secondPart[0]; // firstPart is mode, secondPart is value of mode (data)
        return this.commandNumber;
    }
}

