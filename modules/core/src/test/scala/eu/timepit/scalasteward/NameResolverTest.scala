package eu.timepit.scalasteward

import cats.data.NonEmptyList
import eu.timepit.scalasteward.model.Update.{Group, Single}
import org.scalatest.{FunSuite, Matchers}

class NameResolverTest extends FunSuite with Matchers {

  test("resolve single update name: sttp:core") {
    val update = Single("com.softwaremill.sttp", "core", "1.3.3", NonEmptyList.one("1.3.5"))
    NameResolver.resolve(update) shouldBe "sttp:core"
  }

  test("resolve single update name: typelevel:cats-core") {
    val update = Single("org.typelevel", "cats-core", "0.9.0", NonEmptyList.one("1.0.0"))
    NameResolver.resolve(update) shouldBe "typelevel:cats-core"
  }

  test("resolve single update name: monix") {
    val update = Single("io.monix", "monix", "2.3.3", NonEmptyList.one("3.0.0"))
    NameResolver.resolve(update) shouldBe "monix"
  }

  test("resolve single update name: fs2-core") {
    val update = Single("co.fs2", "fs2-core", "0.9.7", NonEmptyList.one("1.0.0"))
    NameResolver.resolve(update) shouldBe "fs2-core"
  }

  test("resolve group update name when the number of artifacts is less than 3") {
    val update = Group(
      "org.typelevel",
      NonEmptyList.of("cats-core", "cats-free"),
      "0.9.0",
      NonEmptyList.one("1.0.0")
    )
    val expected = "typelevel:cats-core, typelevel:cats-free"
    NameResolver.resolve(update) shouldBe expected
  }

  test("resolve group update name when the number of artifacts is greater than 3") {
    val update = Group(
      "org.typelevel",
      NonEmptyList.of("cats-core", "cats-free", "cats-laws", "cats-macros"),
      "0.9.0",
      NonEmptyList.one("1.0.0")
    )
    val expected = "typelevel:cats-core, typelevel:cats-free, typelevel:cats-laws..."
    NameResolver.resolve(update) shouldBe expected
  }
}