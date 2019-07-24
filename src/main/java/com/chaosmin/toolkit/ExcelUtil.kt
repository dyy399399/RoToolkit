package com.chaosmin.toolkit

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet

/**
 * excel read function
 *
 * @author romani
 * @since 2019-07-24 11:35
 */
object ExcelUtil {
    /**
     * get string type value of this [Sheet] based on cell position
     * @param row index of row in current sheet
     * @param index index of cell in row which got by sheet
     * @param ignore ignore Exception and return empty string, default value is false
     */
    fun Sheet?.valueAt(row: Int, index: Int, ignore: Boolean = false): String {
        return when {
            this == null && ignore -> return ""
            this == null && !ignore -> throw IllegalStateException("current sheet is null.")
            this != null -> {
                when {
                    (0..this.lastRowNum).contains(row) -> this.getRow(row).valueAt(index, ignore)
                    else -> if (ignore) "" else throw IndexOutOfBoundsException("RowNum $row is out of bounds in sheet '${this.sheetName}'")
                }
            }
            else -> ""
        }
    }

    /**
     * get string type value of this [Row] based on cell index
     * @param index index of cell in current row'
     * @param ignore ignore Exception and return empty string, default value is false
     */
    fun Row?.valueAt(index: Int, ignore: Boolean = false): String {
        return when {
            this == null && ignore -> return ""
            this == null && !ignore -> throw IllegalStateException("current row is null.")
            this != null -> {
                when {
                    (0..this.lastCellNum).contains(index) -> this.getCell(index).value(ignore)
                    else -> if (ignore) "" else throw IndexOutOfBoundsException("CellNum is out of bounds in row-line '${this.rowNum}'")
                }
            }
            else -> ""
        }
    }

    /**
     * get string type value of this [Cell] based on [CellType]
     * @param ignore ignore Exception and return empty string, default value is false
     */
    fun Cell?.value(ignore: Boolean = false): String {
        return when {
            this == null && ignore -> return ""
            this == null && !ignore -> throw IllegalStateException("current cell is null.")
            this != null -> {
                when (this.cellType) {
                    CellType._NONE -> ""
                    CellType.BLANK -> ""
                    CellType.ERROR -> if (ignore) "" else throw RuntimeException(this.errorCellValue.toString())
                    CellType.STRING -> this.stringCellValue.format()
                    CellType.FORMULA -> this.stringCellValue.format()
                    CellType.NUMERIC -> if (this.numericCellValue % 1 != 0.0) this.numericCellValue.toString().format()
                    else this.numericCellValue.toInt().toString().format()
                    CellType.BOOLEAN -> this.booleanCellValue.toString().format()
                    else -> ""
                }
            }
            else -> ""
        }
    }
}