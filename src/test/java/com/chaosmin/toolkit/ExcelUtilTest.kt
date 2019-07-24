package com.chaosmin.toolkit

import com.chaosmin.toolkit.ExcelUtil.value
import com.chaosmin.toolkit.ExcelUtil.valueAt
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileNotFoundException

/**
 * @author romani
 * @since 2019-07-24 12:03
 */
class ExcelUtilTest {
    @Test
    fun test() {
        val classLoader = ExcelUtilTest::class.java.classLoader
        val originFile = classLoader.getResource("test.xlsx")?.path
        if (!originFile.isNullOrBlank()) {
            try {
                val file = File(originFile)
                WorkbookFactory.create(file).use { workbook ->
                    val sheet = workbook.getSheetAt(0)
                    assertEquals("test", sheet.valueAt(0, 0))

                    assertEquals("", sheet.valueAt(0, 1, true))
                    assertThrows(IllegalStateException::class.java) { sheet.valueAt(0, 1) }

                    assertEquals("", sheet.valueAt(1, 0, true))
                    assertThrows(IndexOutOfBoundsException::class.java) { sheet.valueAt(1, 0) }

                    val row = sheet.getRow(0)
                    assertEquals("test", row.valueAt(0))

                    assertEquals("", row.valueAt(1, true))
                    assertThrows(IllegalStateException::class.java) { row.valueAt(1) }

                    val cell = row.getCell(0)
                    assertEquals("test", cell.value())

                    val errorCell = row.getCell(1)
                    assertEquals("", errorCell.value(true))
                    assertThrows(IllegalStateException::class.java) { errorCell.value() }
                }
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } else throw RuntimeException("missing excel file: $originFile")
    }
}