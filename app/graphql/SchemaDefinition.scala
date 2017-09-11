package graphql

import sangria.macros.derive._
import sangria.schema._

object SchemaDefinition {

  val TeamType: ObjectType[Unit, Team] = deriveObjectType[Unit, Team](
    ObjectTypeDescription("A team"),
    DocumentField("name", "The name of the team"))

  val ID = Argument("id", IntType, description = "The id of an entity")

  val Query = ObjectType(
    "Query", fields[DataRepository, Unit](
      Field("team", OptionType(TeamType),
        arguments = ID :: Nil,
        resolve = ctx => ctx.ctx.getTeam(ctx arg ID)
      ),
      Field("teams", ListType(TeamType),
        description = Some("Returns the list of teams"),
        resolve = _.ctx.getTeams
      )
    )
  )

  val TeamSchema = Schema(Query)

}
